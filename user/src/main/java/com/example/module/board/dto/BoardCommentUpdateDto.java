package com.example.module.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardCommentUpdateDto {
    @NotEmpty(message = "내용을 작성해주세요.")
    private String contents;
}
