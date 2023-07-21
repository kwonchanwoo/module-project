package com.example.module.board.service;

import com.example.module.board.dto.BoardCommentCreateDto;
import com.example.module.board.dto.BoardCommentDto;
import com.example.module.entity.Board;
import com.example.module.entity.BoardComment;
import com.example.module.repository.BoardCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardCommentService {
    private final BoardCommentRepository boardCommentRepository;

    public Page<BoardCommentDto> getBoardCommentList(Board board, Pageable pageable) {
        return boardCommentRepository
                .findByBoardOrderByIdDesc(board, pageable)
                .map((BoardCommentDto::new));
    }

    @Transactional
    public void postBoardComment(BoardCommentCreateDto boardCommentCreateDto) {
        var boardCommentBuilder = BoardComment.builder()
                .contents(boardCommentCreateDto.getContents());
        Optional.ofNullable(boardCommentCreateDto.getParentComment())
                .ifPresentOrElse(
                        boardCommentBuilder::parent,
                        () -> boardCommentBuilder.board(boardCommentCreateDto.getBoard())
                );
        var boardComment = boardCommentBuilder.build();
        boardCommentRepository.save(boardComment);
    }
    @Transactional
    public void deleteBoardComment(BoardComment boardComment) {
        boardComment.setDeleted(true);
    }

    @Transactional
    public void patchBoardComment(BoardComment boardComment, BoardCommentCreateDto boardCommentCreateDto) {
        boardComment.setContents(boardCommentCreateDto.getContents());
    }
}
