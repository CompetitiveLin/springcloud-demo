package com.example.common.response;

import lombok.Data;

@Data
public final class Result<T> {
    private int code;

    private String message;

    private T data;

    private long timestamp = System.currentTimeMillis();
}
