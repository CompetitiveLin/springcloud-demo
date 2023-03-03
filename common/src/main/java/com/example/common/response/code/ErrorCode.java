package com.example.common.response.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode implements Code{
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    LOCK_IP(423, "IP is locked");;
    private int code;
    private String message;
}
