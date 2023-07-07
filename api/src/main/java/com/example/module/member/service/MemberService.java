package com.example.module.member.service;

import com.example.module.entity.Member;
import com.example.module.member.dto.MemberCreateDto;
import com.example.module.member.dto.MemberDto;
import com.example.module.repository.MemberRepository;
import com.example.module.spec.MemberSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Page<MemberDto> getMemberList(Pageable pageable, Map<String, Object> filters) {
        return memberRepository
                .findAll(MemberSpec.specMember(filters), pageable)
                .map((MemberDto::new));
    }

    public void postMember(MemberCreateDto memberCreateDto) {
        memberRepository.save(
                Member.builder()
                        .name(memberCreateDto.getName())
                        .sex(memberCreateDto.getSex())
                        .age(memberCreateDto.getAge())
                        .phoneNumber(memberCreateDto.getPhoneNumber())
                        .build());

    }

    public MemberDto getMember(Member member) {
        return new MemberDto(member);
    }
}
