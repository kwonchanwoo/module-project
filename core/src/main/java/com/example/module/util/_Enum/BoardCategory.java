package com.example.module.util._Enum;

import com.example.module.util.CommonException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.example.module.util._Enum.ErrorCode.ENUM_BOARD_CATEGORY_INVALID;

public enum BoardCategory {
    BASIC,NOTICE,EVENT;

    @JsonCreator
    public static BoardCategory fromValue(String value) {
        for (BoardCategory boardCategory : BoardCategory.values()) {
            if (boardCategory.name().equalsIgnoreCase(value)) {
                return boardCategory;
            }
        }
        throw new CommonException(ENUM_BOARD_CATEGORY_INVALID);
    }

    @JsonValue
    public String getValue() {
        return this.name();
    }
}
