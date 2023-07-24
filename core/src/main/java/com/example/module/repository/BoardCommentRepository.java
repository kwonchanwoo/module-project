package com.example.module.repository;

import com.example.module.entity.Board;
import com.example.module.entity.BoardComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {
    default Page<BoardComment> findAllBoardCommentCustom(Board board,Pageable pageable){
        return findByBoardAndBoard_DeletedFalseOrderByIdDesc(board,pageable);
    }
    Page<BoardComment> findByBoardAndBoard_DeletedFalseOrderByIdDesc(Board board, Pageable pageable);

}
