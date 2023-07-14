package com.example.module.util.security.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {

    private String accessToken;
    private String refreshToken;
}
