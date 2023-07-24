package com.example.module.board.controller;

import com.example.module.board.dto.request.BoardCreateDto;
import com.example.module.board.dto.response.BoardDetailDto;
import com.example.module.board.dto.response.BoardDto;
import com.example.module.board.service.BoardService;
import com.example.module.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<Page<BoardDto>> getBoardList(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) Map<String, Object> filters) {
        return ResponseEntity.ok(boardService.getBoardList(pageable, filters));
    }

    @GetMapping("{board}")
    public ResponseEntity<BoardDetailDto> getBoardDetail(@PathVariable Board board) {
        return ResponseEntity.ok(boardService.getBoardDetail(board));
    }

    @PostMapping
    public void postBoard(@Valid @RequestBody BoardCreateDto boardCreateDto) {
        boardService.postBoard(boardCreateDto);
    }

    @PatchMapping("{board}")
    public void patchBoard(@PathVariable Board board, @Valid @RequestBody BoardCreateDto boardCreateDto) {
        boardService.patchBoard(board, boardCreateDto);
    }

    @DeleteMapping("{board}")
    public void deleteBoard(@PathVariable Board board) {
        boardService.deleteBoard(board);
    }

}
