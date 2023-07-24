package com.example.module.board.dto;

import com.example.module.entity.BoardComment;
import com.example.module.entity.Member;
import com.example.module.member.dto.MemberDto;
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
    private Long id;
    private Long commentId;
    private String contents;

    private MemberDto memberDto;
    private List<BoardCommentDto> boardCommentsList;


    public BoardCommentDto(BoardComment boardComment) {
        this.id = boardComment.getId();
        this.commentId = boardComment.getId();
        this.contents = boardComment.getContents();
        this.memberDto = new MemberDto(boardComment.getCreatedMember());
        this.boardCommentsList = boardComment.getBoardComments()
                .stream()
                .map(BoardCommentDto::new)
                .collect(Collectors.toList());
    }
}
