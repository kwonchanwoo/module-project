package com.example.module.annotation;

import com.example.module.util._Enum.CommonAction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommonLog {
    String title();
    CommonAction commonAction();
}
