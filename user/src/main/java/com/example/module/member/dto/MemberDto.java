package com.example.module.member.dto;

import com.example.module.entity.Member;
import com.example.module.util._Enum.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String name;

    private String email;
    private Gender sex;
    private int age;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.sex = member.getSex();
        this.age = member.getAge();
        this.phoneNumber = member.getPhoneNumber();
        this.createdAt = member.getCreatedAt();
        this.updatedAt = member.getUpdatedAt();
    }
}
