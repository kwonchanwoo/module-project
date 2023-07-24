package com.example.module.converter;

import com.example.module.entity.Board;
import com.example.module.repository.BoardRepository;
import com.example.module.util.CommonException;
import com.example.module.util._Enum.ErrorCode;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.module.util.security.SecurityContextHelper.getPrincipal;
import static com.example.module.util.security.SecurityContextHelper.isAdmin;


@Component
@RequiredArgsConstructor
public class BoardConverter implements
        org.springframework.core.convert.converter.Converter<String, Board>,
        com.fasterxml.jackson.databind.util.Converter<String, Board> {

    private final BoardRepository repository;

    @Override
    public Board convert(String id) {
        if (getPrincipal() != null && isAdmin()) {
            return repository.findById(Long.parseLong(id)).orElseThrow(() -> new CommonException(ErrorCode.ACCESS_DENIED));
        }
        return repository.findByIdAndDeletedFalse(Long.parseLong(id)).orElseThrow(() -> new CommonException(ErrorCode.ACCESS_DENIED));
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructType(String.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(Board.class);
    }
}
