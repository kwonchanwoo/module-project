package com.example.module.converter;

import com.example.module.entity.Member;
import com.example.module.repository.MemberRepository;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberConverter implements
        org.springframework.core.convert.converter.Converter<String, Member>,
        com.fasterxml.jackson.databind.util.Converter<String, Member> {

    private final MemberRepository repository;

    @Override
    public Member convert(String fileId) {
        return repository.findById(Long.valueOf(fileId)).orElseThrow(() -> new RuntimeException("member not found"));
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructType(String.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(Member.class);
    }
}
