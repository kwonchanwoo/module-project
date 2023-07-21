package com.example.module.board.controller;

import com.example.module.board.dto.BoardCommentCreateDto;
import com.example.module.board.dto.BoardCommentDto;
import com.example.module.board.service.BoardCommentService;
import com.example.module.entity.Board;
import com.example.module.entity.BoardComment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("board-comment")
public class BoardCommentController {
    private final BoardCommentService boardCommentService;

    @GetMapping("{board}")
    public ResponseEntity<Page<BoardCommentDto>> getBoardCommentList(@PathVariable Board board, Pageable pageable){
        return ResponseEntity.ok(boardCommentService.getBoardCommentList(board, pageable));
    }

    @PostMapping
    public void postBoardComment(@RequestBody BoardCommentCreateDto boardCommentCreateDto){
        boardCommentService.postBoardComment(boardCommentCreateDto);
    }

    @PatchMapping
    public void patchBoardComment(@PathVariable BoardComment boardComment,BoardCommentCreateDto boardCommentCreateDto){
        boardCommentService.patchBoardComment(boardComment,boardCommentCreateDto);
    }

    @DeleteMapping("{boardComment}")
    public void deleteBoardComment(@PathVariable BoardComment boardComment){
        boardCommentService.deleteBoardComment(boardComment);
    }
}
