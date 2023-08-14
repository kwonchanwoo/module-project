package com.example.module.board.service;

import com.example.module.annotation.CommonLog;
import com.example.module.board.dto.request.BoardCommentCreateDto;
import com.example.module.board.dto.response.BoardCommentDto;
import com.example.module.board.dto.request.BoardCommentUpdateDto;
import com.example.module.entity.Board;
import com.example.module.entity.BoardComment;
import com.example.module.repository.BoardCommentRepository;
import com.example.module.util._Enum.CommonAction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.module.util.security.SecurityContextHelper.isAuthorizedForMember;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardCommentService {
    private final BoardCommentRepository boardCommentRepository;

    @CommonLog(title="게시판 댓글 목록 조회",commonAction= CommonAction.SELECT)
    public Page<BoardCommentDto> getBoardCommentList(Board board, Pageable pageable) {
        // Todo: 삭제된 게시판의 댓글은 보이지않도록 처리해야함
        return boardCommentRepository
                .findAllBoardCommentCustom(board, pageable)
                .map((BoardCommentDto::new));
    }

    @Transactional
    @CommonLog(title="게시판 댓글 등록",commonAction= CommonAction.INSERT)
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
    @CommonLog(title="게시판 댓글 삭제",commonAction= CommonAction.DELETE)
    public void deleteBoardComment(BoardComment boardComment) {
        isAuthorizedForMember(boardComment.getCreatedMember());
        boardComment.setDeleted(true);
    }

    @Transactional
    @CommonLog(title="게시판 댓글 수정",commonAction= CommonAction.UPDATE)
    public void patchBoardComment(BoardComment boardComment, BoardCommentUpdateDto boardCommentUpdateDto) {
        isAuthorizedForMember(boardComment.getCreatedMember());
        boardComment.setContents(boardCommentUpdateDto.getContents());
    }
}
