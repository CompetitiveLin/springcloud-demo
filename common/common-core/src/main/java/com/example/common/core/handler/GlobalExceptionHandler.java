package com.example.common.core.handler;

import com.example.common.core.exception.CustomException;
import com.example.common.core.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus
    @ExceptionHandler(value = CustomException.class)
    public ResponseResult handle(CustomException e) {
        if (e.getErrorCode() != null) {
            log.error(e.getErrorCode().getMessage());
            return ResponseResult.failed(e.getErrorCode());
        }
        log.error(e.getMessage());
        return ResponseResult.failed(e.getMessage());
    }
}
