//package com.example.core.config;
//
//import com.example.core.repository.MemberRepository;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//
//@Configuration
//@ComponentScan({"com.example.core.repository"})
//@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
//public class JpaAuditConfiguration {
//
//    private final MemberRepository memberRepository;
//
//    public JpaAuditConfiguration(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
////    @Bean
////    public AuditorAware<Member> auditorProvider() {
////        return new AuditorAwareImpl(memberRepository);
////    }
////}
//}
