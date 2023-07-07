package com.example.module.member.dto;

import com.example.module.util._Enum.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateDto {
    private String name;
    private Gender sex;
    private int age;
    private String phoneNumber;
}
