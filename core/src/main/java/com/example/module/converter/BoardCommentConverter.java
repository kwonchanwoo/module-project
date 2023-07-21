package com.example.module.converter;

import com.example.module.entity.BoardComment;
import com.example.module.repository.BoardCommentRepository;
import com.example.module.util.CommonException;
import com.example.module.util._Enum.ErrorCode;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardCommentConverter implements
        org.springframework.core.convert.converter.Converter<String, BoardComment>,
        com.fasterxml.jackson.databind.util.Converter<String, BoardComment> {

    private final BoardCommentRepository repository;

    @Override
    public BoardComment convert(String id) {
        return repository.findById(Long.parseLong(id)).orElseThrow(() -> new CommonException(ErrorCode.BOARD_COMMENT_NOT_FOUND));
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructType(String.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(BoardComment.class);
    }
}
