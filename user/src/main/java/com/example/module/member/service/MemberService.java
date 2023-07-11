package com.example.module.member.service;

import com.example.module.entity.Member;
import com.example.module.member.dto.MemberCreateDto;
import com.example.module.member.dto.MemberDto;
import com.example.module.repository.MemberRepository;
import com.example.module.spec.MemberSpec;
import com.example.module.util.CommonException;
import com.example.module.util._Enum.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

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
    public void deleteMember(Member member) {
        // Todo: 실제 로그인한사람하고 탈퇴하는사람이 같은지 체크가 필요할거같음(User) , admin은 별도 체크 X
        member.setDeleted(true);
    }

}
