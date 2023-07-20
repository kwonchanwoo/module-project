package com.example.module.board.dto;

import com.example.module.entity.Board;
import com.example.module.member.dto.MemberDto;
import com.example.module.util._Enum.BoardCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Long id;
    private String title;
    private String contents;
    private BoardCategory boardCategory;
    private List<BoardCommentDto> boardCommentDto;
    private MemberDto createdMember;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BoardDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.boardCategory = board.getBoardCategory();
        this.boardCommentDto = board.getBoardComments()
                .stream()
                .map((BoardCommentDto::new))
                .collect(Collectors.toList());
        this.createdMember = new MemberDto(board.getCreatedMember());
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
    }
}
