package com.example.common.core.handler;

import com.example.common.core.exception.CustomException;
import com.example.common.core.response.ResponseResult;
import com.example.common.core.util.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Component
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = CustomException.class)
    public ResponseResult<?> handle(CustomException e) {
        if (e.getErrorCode() != null) {
            log.error(e.getErrorCode().getMessage());
            return ResponseResult.failed(e.getErrorCode());
        }
        log.error(e.getMessage());
        return ResponseResult.failed(e.getMessage());
    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Throwable.class)
    public ResponseResult<?> unknownException(Throwable e){
        log.error(ThrowableUtil.getStackTrace(e));
        return ResponseResult.failed(e.getMessage());
    }
}
