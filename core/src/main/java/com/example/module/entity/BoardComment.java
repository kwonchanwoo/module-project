package com.example.module.entity;

import com.example.module.util.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BoardComment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Lob
    private String contents;

    @ManyToOne
    @JoinColumn(name = "target_id")
    private BoardComment targetId; // 자기 자신을 참조하는 관계

    @Column(nullable = false)
    private int commentOrder; // 게시판 댓글 순서

}
