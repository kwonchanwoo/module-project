package com.example.module.util._Enum;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // token
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, 401, "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.FORBIDDEN, 403, "토큰의 유효기간 만료"),

    TOKEN_ACCESS_DENIED(HttpStatus.FORBIDDEN, 403, "잘못된 접근입니다."), // 권한이 없다고 표현하면 악용 될수있어서 잘못된 접근으로 처리

    TOKEN_UNSUPPORTED(HttpStatus.BAD_REQUEST, 400, "지원되지 않는 JWT 토큰 형식입니다."),

    // member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Member not found"),
    MEMBER_DUPLICATED(HttpStatus.CONFLICT, 409, "Member is duplicated"),

    // board
    BOARD_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Board_Comment not found"),
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Member not found"),

    // login
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED,401,"로그인에 실패하였습니다."),

    // enum
    ENUM_GENDER_INVALID(HttpStatus.BAD_REQUEST,400,"성별을 잘못 입력하셨습니다.");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
