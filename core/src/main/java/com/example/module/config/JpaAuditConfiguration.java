package com.example.module.config;

import com.example.module.annotation.RedisRepository;
import com.example.module.entity.Member;
import com.example.module.util.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@ComponentScan(
        basePackages = "com.example.module.repository",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = RedisRepository.class)
)
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditConfiguration {

    @Bean
    public AuditorAware<Member> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
