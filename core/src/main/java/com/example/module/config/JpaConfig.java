package com.example.module.config;

import com.example.module.annotation.RedisRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * redis에서 사용하는 repository를 annotation으로 제외
 * 그외 나머지는 repository로 빈 등록
 */
@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.module.repository",
        excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION, classes = RedisRepository.class)
)
public class JpaConfig {

}
