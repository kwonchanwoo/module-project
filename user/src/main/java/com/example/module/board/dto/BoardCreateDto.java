package com.example.module.board.dto;

import com.example.module.util._Enum.BoardCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BoardCreateDto {
    private String title;
    private String contents;
    private BoardCategory boardCategory;

}
