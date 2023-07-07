package com.example.module.member.controller;

import com.example.module.entity.Member;
import com.example.module.member.dto.MemberCreateDto;
import com.example.module.member.dto.MemberDto;
import com.example.module.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("members")
public class MemberController {

    private final MemberService memberService;

    // 목록
    @GetMapping
    public ResponseEntity<Page<MemberDto>> getMemberList(
            Pageable pageable,
            @RequestParam(required = false) Map<String, Object> filters) {
        return ResponseEntity.ok(memberService.getMemberList(pageable, filters));
    }

    // 상세
    @GetMapping("{member}")
    public MemberDto getMember(
            @PathVariable Member member
    ) {
        return memberService.getMember(member);
    }

    // 가입
    @PostMapping
    public void postMember(@RequestBody MemberCreateDto memberCreateDto) {
        memberService.postMember(memberCreateDto);
    }

}
