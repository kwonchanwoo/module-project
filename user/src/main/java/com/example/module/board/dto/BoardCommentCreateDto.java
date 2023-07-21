package com.example.module.board.dto;

import com.example.module.converter.BoardCommentConverter;
import com.example.module.converter.BoardConverter;
import com.example.module.entity.Board;
import com.example.module.entity.BoardComment;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardCommentCreateDto {
    private String contents;
    @JsonDeserialize(converter = BoardConverter.class)
    private Board board;
    @JsonDeserialize(converter = BoardCommentConverter.class)
    private BoardComment parentComment;

}

