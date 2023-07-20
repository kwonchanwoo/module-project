package com.example.module.board.controller;

import com.example.module.board.dto.BoardCreateDto;
import com.example.module.board.dto.BoardDto;
import com.example.module.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("boards")
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<Page<BoardDto>> getBoardList(Pageable pageable,@RequestParam(required = false) Map<String, Object> filters) {
        return ResponseEntity.ok(boardService.getBoardList(pageable, filters));
    }

    @PostMapping
    public void postBoard(@Valid @RequestBody BoardCreateDto boardCreateDto) {
        boardService.postBoard(boardCreateDto);
    }
}
