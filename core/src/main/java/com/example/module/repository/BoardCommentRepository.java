package com.example.module.repository;

import com.example.module.entity.Board;
import com.example.module.entity.BoardComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {
    Page<BoardComment> findByBoardOrderByIdDesc(Board board, Pageable pageable);

}
