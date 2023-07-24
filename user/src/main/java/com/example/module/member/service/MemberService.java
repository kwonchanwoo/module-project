package com.example.module.member.service;

import com.example.module.entity.Member;
import com.example.module.member.dto.MemberCreateDto;
import com.example.module.member.dto.MemberDto;
import com.example.module.repository.MemberRepository;
import com.example.module.repository.RefreshTokenRepository;
import com.example.module.spec.MemberSpec;
import com.example.module.util.CommonException;
import com.example.module.util._Enum.ErrorCode;
import com.example.module.util.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.example.module.util.security.SecurityContextHelper.getPrincipal;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    private final RefreshTokenRepository refreshTokenRepository;

    public Page<MemberDto> getMemberList(Pageable pageable, Map<String, Object> filters) {
        return memberRepository
                .findAll(MemberSpec.specMember(filters), pageable)
                .map((MemberDto::new));
    }

    public MemberDto getMember(Member member) {
        return new MemberDto(member);
    }

    @Transactional
    public void join(MemberCreateDto memberCreateDto) {
        memberRepository.findByEmail(memberCreateDto.getEmail())
                .ifPresent((member -> {
                    throw new CommonException(ErrorCode.MEMBER_DUPLICATED);
                }));
        memberRepository.save(
                Member.builder()
                        .name(memberCreateDto.getName())
                        .email(memberCreateDto.getEmail())
                        .password(passwordEncoder.encode(memberCreateDto.getPassword()))
                        .sex(memberCreateDto.getSex())
                        .age(memberCreateDto.getAge())
                        .phoneNumber(memberCreateDto.getPhoneNumber())
                        .roles(List.of("USER"))
                        .build());

    }

    @Transactional
    public void deleteMember(String accessToken) {
        accessToken = resolveToken(accessToken);
        //실제 로그인한사람하고 탈퇴하는사람이 같은지 체크가 필요할거같음(User) , admin은 별도 체크 X
        getPrincipal().setDeleted(true);
        //1. accessToken을 블랙리스트에 등록 (RestTemplate로 남은시간만큼 시간설정
        redisTemplate.opsForValue().set(
                accessToken, "blacklisted", jwtTokenProvider.getExpiration(accessToken), TimeUnit.MILLISECONDS
        );
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

}
