package com.example.module.member.dto;

import com.example.module.util._Enum.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateDto {
    private String name;

    private String email;

    private String password;

    private Gender sex;

    private int age;
    private String phoneNumber;
}
