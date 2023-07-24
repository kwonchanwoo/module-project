package com.example.module.board.dto.request;

import com.example.module.converter.BoardCommentConverter;
import com.example.module.converter.BoardConverter;
import com.example.module.entity.Board;
import com.example.module.entity.BoardComment;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardCommentCreateDto {
    @NotBlank(message = "내용을 입력 해 주세요.")
    private String contents;

    @JsonDeserialize(converter = BoardConverter.class)
    private Board board;
    @JsonDeserialize(converter = BoardCommentConverter.class)
    private BoardComment parentComment;

}

