//package com.example.core.util;
//
//import com.example.core.entity.Member;
//import com.example.core.repository.MemberRepository;
//import kr.co.jeit.entity.User;
//import kr.co.jeit.repository.UserRepository;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.util.Optional;
//
//
////public class AuditorAwareImpl implements AuditorAware<Member> {
////
////    private final MemberRepository memberRepository;
////
////    public AuditorAwareImpl(MemberRepository memberRepository) {
////        this.memberRepository = memberRepository;
////    }
////
////    @Override
////    public Optional<Member> getCurrentAuditor() {
//////        현재 로그인한 SecurityContextHolder 에서 정보를 가져옴
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//////        만일 로그인한 정보가 null 이 아니고 인증이 되어있고 name 이 anonymousUser(인증되지 않은 유저)가 아닐 경우 로그인 처리
////        if (
////            authentication == null
////                || !authentication.isAuthenticated()
////                || authentication.getName().equals("anonymousUser")
////        ) {
//////            로그인이 되어있지 않을 경우 empty 를 입력
////            return Optional.empty();
////        }
//////        로그인이 되어있을 시 해당 정보의 Name 에 저장되어있는 seq 번호로 UserRepository 에서 유저정보를 찾아서 반환
////        return userRepository.findById(Long.parseLong(authentication.getName()));
//////        return userRepository.findById(1L);
////    }
//}
