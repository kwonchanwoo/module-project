package com.example.module.authorize.service;

import com.example.module.authorize.dto.LoginDto;
import com.example.module.entity.Member;
import com.example.module.repository.MemberRepository;
import com.example.module.repository.RefreshTokenRepository;
import com.example.module.util.redis.RefreshToken;
import com.example.module.util.security.JwtTokenProvider;
import com.example.module.util.security.dto.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorizeService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

//    private final RedisTemplate redisTemplate;  //RedisTemplate 방식

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenInfo login(LoginDto loginDto) {
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

        //4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리) => RedisTemplate 방식
//        redisTemplate.opsForValue()
//                .set("RT:" + authentication.getName(),
//                        tokenInfo.getRefreshToken(),
//                        tokenInfo.getRefreshTokenExpirationTime(),
//                        TimeUnit.MILLISECONDS
//
//                );

        refreshTokenRepository.save(new RefreshToken(authentication.getName(), tokenInfo.getRefreshToken()));

        return tokenInfo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
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
        // Todo: 1. refreshToken의 만료 체크(validation)
        jwtTokenProvider.validateToken(tokenInfo.getRefreshToken());
        // Todo: 2. acessToken으로 user정보 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenInfo.getAccessToken());
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user 정보가 없습니다."));
        // Todo: 3 refreshToken과 갖고온 user 정보로 refreshToken 존재 여부 확인
        refreshTokenRepository
                .findByEmailAndToken(member.getEmail(), tokenInfo.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("토큰이 올바르지 않습니다."));

        // Todo: 4 accessToken 재발급
        return jwtTokenProvider.generateToken(authentication, tokenInfo.getRefreshToken());
    }
}
