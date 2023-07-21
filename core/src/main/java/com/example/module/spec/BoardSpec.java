package com.example.module.spec;

import com.example.module.entity.Board;
import com.example.module.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@RequiredArgsConstructor
public class BoardSpec {
    private static void removePagerValue(Map<String, Object> map) {
        map.remove("page");
        map.remove("size");
        map.remove("sort");
    }

    private static boolean isAdmin(){
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> authorities = member.getAuthorities();
        return authorities.contains("ADMIN");
    }

    public static Specification<Board> specBoard(Map<String, Object> map) {
        removePagerValue(map);

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>(Collections.emptyList());
            if (!isAdmin()) {
                predicates.add(cb.equal(root.get("deleted"), false));
            }

            map.forEach((key, value) -> {
                switch (key) {
                    case "startDate":
                        predicates.add(
                                cb.greaterThanOrEqualTo( //~ 이상
                                        root.get("createdAt"), //컬럼값
                                        LocalDateTime.of(
                                                LocalDate.parse(
                                                        String.valueOf(value),
                                                        DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                                ),
                                                LocalTime.MIN
                                        )
                                )
                        );
                        break;
                    case "endDate":
                        predicates.add(
                                cb.lessThanOrEqualTo( //~ 이하
                                        root.get("createdAt"), //컬럼값
                                        LocalDateTime.of(
                                                LocalDate.parse(
                                                        String.valueOf(value),
                                                        DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                                ),
                                                LocalTime.MAX
                                        )
                                )
                        );
                        break;
                    case "createdMember":
                        predicates.add(
                                cb.equal(root.get("createdMember").get("id"), value)
                        );
                        break;
                    default:
                        predicates.add(cb.like(root.get(key), "%" + value + "%"));
                        break;
                }
            });
            return cb.and(predicates.toArray(new Predicate[0])); // 가변인수
        };
    }
}
