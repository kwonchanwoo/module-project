package com.example.module.board.dto;

import com.example.module.entity.Board;
import com.example.module.entity.BoardComment;
import com.example.module.member.dto.MemberDto;
import com.example.module.util._Enum.BoardCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDetailDto {
    private Long id;
    private String title;
    private String contents;
    private BoardCategory boardCategory;
    private int commentCount;
    private MemberDto createdMember;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BoardDetailDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.boardCategory = board.getBoardCategory();
        this.commentCount = board.getBoardComments().stream().mapToInt(this::countChildBoardComment).sum();
        this.createdMember = new MemberDto(board.getCreatedMember());
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
    }

    private int countChildBoardComment(BoardComment boardComment) {
        //Step 1 : 종료 조건을 먼저 적는다
        if (boardComment.getBoardComments() == null || boardComment.getBoardComments().isEmpty()) //내 밑에 녀석이 아무도 없을 때 재귀 종료
            return 1;
        //Step 2 : 다시 나를 부를 (재귀 할)조건을 적는다
        //내 자식 들 의 수 :  boardComment.getBoardComments().stream().mapToInt(this::countChildBoardComment).sum()
        return 1 + boardComment.getBoardComments().stream().mapToInt(this::countChildBoardComment).sum();//+1은 나 자신
    }
}
