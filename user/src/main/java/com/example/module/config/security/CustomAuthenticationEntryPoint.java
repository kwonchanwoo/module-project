package com.example.module.config.security;

import com.example.module.util._Enum.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ErrorCode errorCode = (ErrorCode)request.getAttribute("exception");
        setResponse(response,errorCode);

//        /**
//         * 토큰 없는 경우
//         */
//        if(exception == null) {
//            errorCode = ErrorCode.NON_LOGIN;
//            setResponse(response, errorCode);
//            return;
//        }
//
//        /**
//         * 토큰 만료된 경우
//         */
//        if(errorCode.equals(ErrorCode.TOKEN_EXPIRED)) {
//            errorCode = ErrorCode.TOKEN_EXPIRED;
//            setResponse(response, errorCode);
//            return;
//        }
//
//        /**
//         * 토큰 시그니처가 다른 경우
//         */
//        if(exception.equals(ErrorCode.TOKEN_INVALID.getCode().toString())) {
//            errorCode = ErrorCode.TOKEN_INVALID;
//            setResponse(response, errorCode);
//        }
//
//        /**
//         * 토큰 시그니처가 다른 경우
//         */
//        if(exception.equals(ErrorCode.TOKEN_UNSUPPORTED.getCode().toString())) {
//            errorCode = ErrorCode.TOKEN_UNSUPPORTED;
//            setResponse(response, errorCode);
//        }
    }

    /**
     * 한글 출력을 위해 getWriter() 사용
     */
    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.getCode());
        response.getWriter().println("{ \"message\" : \"" + errorCode.getMessage()
                + "\", \"code\" : \"" +  errorCode.getCode()
                + "\", \"status\" : " + errorCode.getHttpStatus());
    }

}
