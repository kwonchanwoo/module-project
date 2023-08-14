package com.example.module.board.service;

import com.example.module.annotation.CommonLog;
import com.example.module.board.dto.request.BoardCreateDto;
import com.example.module.board.dto.response.BoardDetailDto;
import com.example.module.board.dto.response.BoardDto;
import com.example.module.entity.Board;
import com.example.module.repository.BoardRepository;
import com.example.module.util._Enum.CommonAction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.example.module.spec.BoardSpec.specBoard;
import static com.example.module.util.security.SecurityContextHelper.isAuthorizedForMember;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;

    @CommonLog(title="게시판 목록 조회",commonAction= CommonAction.SELECT)
    public Page<BoardDto> getBoardList(Pageable pageable, Map<String, Object> filters) {
        return boardRepository
                .findAll(specBoard(filters), pageable)
                .map((BoardDto::new));
    }

    @CommonLog(title="게시판 상세",commonAction= CommonAction.SELECT)
    public BoardDetailDto getBoardDetail(Board board) {
        return new BoardDetailDto(board);
    }

    @Transactional
    @CommonLog(title="게시판 등록",commonAction= CommonAction.INSERT)
    public void postBoard(BoardCreateDto boardCreateDto) {
        boardRepository.save(
                Board.builder()
                        .title(boardCreateDto.getTitle())
                        .contents(boardCreateDto.getContents())
                        .boardCategory(boardCreateDto.getBoardCategory())
                        .build()
        );
    }

    @Transactional
    @CommonLog(title="게시판 삭제",commonAction= CommonAction.DELETE)
    public void deleteBoard(Board board) {
        isAuthorizedForMember(board.getCreatedMember());
        board.setDeleted(true);
    }

    @Transactional
    @CommonLog(title="게시판 수정",commonAction= CommonAction.UPDATE)
    public void patchBoard(Board board, BoardCreateDto boardCreateDto) {
        isAuthorizedForMember(board.getCreatedMember());
        board.builder()
                .title(boardCreateDto.getTitle())
                .contents(boardCreateDto.getContents())
                .boardCategory(boardCreateDto.getBoardCategory())
                .build();
    }
}
