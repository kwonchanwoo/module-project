package com.example.module.repository;

import com.example.module.annotation.RedisRepository;
import com.example.module.util.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@RedisRepository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken,String> {
    Optional<RefreshToken> findByEmailAndToken(String email,String token);
}
