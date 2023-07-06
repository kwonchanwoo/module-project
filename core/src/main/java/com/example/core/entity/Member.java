package com.example.core.entity;

import com.example.core.util.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@Entity
@Getter
@Setter
@MappedSuperclass
public class Member extends BaseEntity {

    private String name;
    private String sex;
    private int age;
    private String phoneNumber;

}
