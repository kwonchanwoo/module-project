package com.example.module.config;

import com.example.module.annotation.RedisRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.module.repository",
        excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION, classes = RedisRepository.class)
)
public class JpaConfig {

}
