package com.example.common.core.response.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BasicCode implements Code{
    SUCCESS(200, "Success"),
    FAILED(500, "Failed");

    private int code;
    private String message;
}
