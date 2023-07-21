package com.example.module.entity;

import com.example.module.util.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne()
    @JoinColumn(name = "parent_id")
    private BoardComment parent;

    @Lob
    private String contents;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardComment> boardComments = new ArrayList<>();
}
