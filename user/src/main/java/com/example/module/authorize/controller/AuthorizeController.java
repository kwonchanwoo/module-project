package com.example.module.authorize.controller;

import com.example.module.authorize.dto.LoginDto;
import com.example.module.authorize.service.AuthorizeService;
import com.example.module.util.security.dto.TokenInfo;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("authorize")
public class AuthorizeController {
    private final AuthorizeService authorizeService;

    @PostMapping("/login")
    public TokenInfo login(
            @Valid @RequestBody LoginDto loginDto
    ) {
        return authorizeService.login(loginDto);
    }

    @GetMapping("/refresh-token")
    public TokenInfo refreshToken(
            @RequestBody TokenInfo tokenInfo
    ) {
        return authorizeService.refreshToken(tokenInfo);
    }

    @DeleteMapping("/logout")
    public void logout(@RequestHeader(name = "Authorization") String accessToken){
        authorizeService.logout(accessToken);
    }


    @ExceptionHandler({SecurityException.class, MalformedJwtException.class, IllegalArgumentException.class, ExpiredJwtException.class, UnsupportedJwtException.class})
    public ResponseEntity<Void> jwtExceptionHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
