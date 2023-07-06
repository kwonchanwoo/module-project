package com.example.api.member.service;

import com.example.api.member.dto.MemberDto;
import com.example.core.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.example.core.spec.MemberSpec.specMember;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Page<MemberDto> getMemberList(Pageable pageable, Map<String, Object> filters) {
       return  memberRepository
               .findAll(specMember(filters),pageable)
               .map((MemberDto::new));
    }
}
