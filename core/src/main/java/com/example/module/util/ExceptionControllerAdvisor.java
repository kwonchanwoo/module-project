package com.example.module.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvisor {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<String> application(CommonException e) {
        return ResponseEntity.status(e.getEnumErrorCode().getHttpStatus())
            .body(e.getMessage());
    }
}
