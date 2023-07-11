package com.example.module.authorize.controller;

import com.example.module.authorize.dto.LoginDto;
import com.example.module.authorize.service.AuthorizeService;
import com.example.module.util.security.dto.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("authorize")
public class AuthorizeController {
    private final AuthorizeService authorizeService;

    @PostMapping("/login")
    public TokenInfo login(
            @RequestBody LoginDto loginDto
    ) {
        return authorizeService.login(loginDto);
    }
}
