package com.example.module.member.controller;

import com.example.module.entity.Member;
import com.example.module.member.dto.MemberCreateDto;
import com.example.module.member.dto.MemberDto;
import com.example.module.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("members")
public class MemberController {

    private final MemberService memberService;

    // 목록
    @GetMapping
    public ResponseEntity<Page<MemberDto>> getMemberList(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
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
    @PostMapping("/join")
    public void join(@Valid  @RequestBody MemberCreateDto memberCreateDto) {
        memberService.join(memberCreateDto);
    }

    // 회원 탈퇴
    @DeleteMapping
    public void deleteMember(
            @RequestHeader(name = "Authorization") String accessToken
    ) {
        memberService.deleteMember(accessToken);
    }
}
