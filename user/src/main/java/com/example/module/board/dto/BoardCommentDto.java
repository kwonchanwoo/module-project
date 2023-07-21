package com.example.module.board.dto;

import com.example.module.entity.BoardComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardCommentDto {

    private Long commentId;
    private String contents;
    private List<BoardCommentDto> boardCommentsList;


    public BoardCommentDto(BoardComment boardComment) {
        this.commentId = boardComment.getId();
        this.contents = boardComment.getContents();
        this.boardCommentsList = boardComment.getBoardComments()
                .stream()
                .map(BoardCommentDto::new)
                .collect(Collectors.toList());
    }
}
