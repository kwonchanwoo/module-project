package com.example.module.board.service;

import com.example.module.board.dto.BoardCreateDto;
import com.example.module.board.dto.BoardDto;
import com.example.module.entity.Board;
import com.example.module.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Map;

import static com.example.module.spec.BoardSpec.specBoard;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Page<BoardDto> getBoardList(Pageable pageable, Map<String, Object> filters) {
        return boardRepository
                .findAll(specBoard(filters), pageable)
                .map((BoardDto::new));
    }

    public void postBoard(BoardCreateDto boardCreateDto) {
        boardRepository.save(
                Board.builder()
                        .title(boardCreateDto.getTitle())
                        .contents(boardCreateDto.getContents())
                        .boardCategory(boardCreateDto.getBoardCategory())
                        .build()
        );
    }
}
