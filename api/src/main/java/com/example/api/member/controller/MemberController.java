package com.example.api.member.controller;

import com.example.api.member.dto.MemberDto;
import com.example.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<Page<MemberDto>> getMemberList(Pageable pageable, Map<String,Object> filters){
        return ResponseEntity.ok(memberService.getMemberList(pageable,filters));
    }

}
