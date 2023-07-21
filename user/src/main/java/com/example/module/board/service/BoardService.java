package com.example.module.board.service;

import com.example.module.board.dto.BoardCreateDto;
import com.example.module.board.dto.BoardDetailDto;
import com.example.module.board.dto.BoardDto;
import com.example.module.entity.Board;
import com.example.module.entity.Member;
import com.example.module.repository.BoardRepository;
import com.example.module.util.CommonException;
import com.example.module.util._Enum.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.example.module.spec.BoardSpec.specBoard;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;

    public Page<BoardDto> getBoardList(Pageable pageable, Map<String, Object> filters) {
        return boardRepository
                .findAll(specBoard(filters), pageable)
                .map((BoardDto::new));
    }

    public BoardDetailDto getBoardDetail(Board board) {
        return new BoardDetailDto(board);
    }

    @Transactional
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
    public void deleteBoard(Board board) {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!member.equals(board.getCreatedMember())) {
            throw new CommonException(ErrorCode.TOKEN_ACCESS_DENIED);
        }
        board.setDeleted(true);
    }

    @Transactional
    public void patchBoard(Board board, BoardCreateDto boardCreateDto) {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!member.equals(board.getCreatedMember())) {
            throw new CommonException(ErrorCode.TOKEN_ACCESS_DENIED);
        }
        board.builder()
                .title(boardCreateDto.getTitle())
                .contents(boardCreateDto.getContents())
                .boardCategory(boardCreateDto.getBoardCategory())
                .build();
    }
}
