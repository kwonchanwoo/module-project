package com.example.module.authorize.service;

import com.example.module.authorize.dto.LoginDto;
import com.example.module.entity.Member;
import com.example.module.repository.MemberRepository;
import com.example.module.repository.RefreshTokenRepository;
import com.example.module.util.CommonException;
import com.example.module.util._Enum.ErrorCode;
import com.example.module.util.redis.RefreshToken;
import com.example.module.util.security.JwtTokenProvider;
import com.example.module.util.security.dto.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorizeService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    private final RedisTemplate<String, Object> redisTemplate;

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenInfo login(LoginDto loginDto) {
        try {
            // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
            // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(), loginDto.getPassword()
            );

            // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
            // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

            // 4-1. RefreshToken redis 저장 (RedisRepository 방식)
            refreshTokenRepository.save(new RefreshToken(authentication.getName(), tokenInfo.getRefreshToken()));
            return tokenInfo;
        } catch (AuthenticationException ex) { // 인증 실패
            throw new CommonException(ErrorCode.LOGIN_FAILED);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                // 접속한 유저의 권한이 User(일반회원) 일때, 계정이 삭제됫는지 체크
                .filter(Member::isEnabled)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Member member) {
        return User.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .roles(member.getRoles().toArray(new String[0]))
                .build();
    }

    public TokenInfo refreshToken(
            TokenInfo tokenInfo
    ) {
        // 1. refreshToken의 만료 체크(validation)
        jwtTokenProvider.validateToken(tokenInfo.getRefreshToken());

        // 2. acessToken으로 user정보 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenInfo.getAccessToken());
        Member member = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new CommonException(ErrorCode.ACCESS_DENIED));

        // 3. refreshToken과 갖고온 user 정보로 refreshToken 존재 여부 확인
        if (refreshTokenRepository.findByEmailAndToken(member.getEmail(), tokenInfo.getRefreshToken()).isEmpty()) {
            throw new CommonException(ErrorCode.TOKEN_INVALID);
        }
        //4. accessToken을 블랙리스트에 등록 (RestTemplate로 남은시간만큼 시간설정
        addBlackList(tokenInfo.getAccessToken());

        // 5. accessToken 재발급
        return jwtTokenProvider.generateToken(authentication, tokenInfo.getRefreshToken());
    }

    public void logout(String accessToken) {
        accessToken = resolveToken(accessToken);
        //1. accessToken을 블랙리스트에 등록 (RestTemplate로 남은시간만큼 시간설정
        addBlackList(accessToken);
        //1-1. 로그인한 유저 정보 갖고오기
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        //2. redis 에 들어있는 refreshToken 정보를 삭제
        refreshTokenRepository.deleteById(authentication.getName());
    }

    private String resolveToken(String accessToken) {
        if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer")) {
            return accessToken.substring(7);
        }
        return null;
    }

    private void addBlackList(String accessToken){
        redisTemplate.opsForValue().set(
                accessToken, "blacklisted", jwtTokenProvider.getExpiration(accessToken), TimeUnit.MILLISECONDS
        );
    }
}
