package com.example.module.board.dto;

import com.example.module.entity.BoardComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardCommentDto {

    private String contents;
    private Long targetId;
    private int commentOrder;


    public BoardCommentDto(BoardComment boardComment) {
        this.contents = boardComment.getContents();
        this.targetId = boardComment.getId();
        this.commentOrder = boardComment.getCommentOrder();
    }
}
