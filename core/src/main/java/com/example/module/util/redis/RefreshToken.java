package com.example.module.util.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;


@Getter
@Setter
@RedisHash(value="refreshToken",timeToLive = 604800000) // timeToLive : 기본 단위 seconds, default : -1 ( 만료시간X)
public class RefreshToken {

    @Id
    @Indexed
    private String email;

    @Indexed
    private String token;

    public RefreshToken(String email,String token){
        this.email = email;
        this.token = token;
    }
}
