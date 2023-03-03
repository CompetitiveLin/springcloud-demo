package com.example.common.response;

import com.example.common.response.code.BasicCode;
import com.example.common.response.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class ResponseResult<T> {
    private int code;

    private String message;

    private T data;

    /**
     * 请求成功
     * @return
     * @param <T>
     */
    public static <T> ResponseResult<T> success(){
        return success(null);
    }

    public static <T> ResponseResult<T> success(T data){
        return new ResponseResult<T>(BasicCode.SUCCESS.getCode(), BasicCode.SUCCESS.getMessage(), data);
    }

    /**
     * 请求失败的返回值一般没有data数据
     * @return
     * @param <T>
     */
    public static <T> ResponseResult<T> failed(){
        return failed(BasicCode.FAILED.getMessage());
    }

    public static <T> ResponseResult<T> failed(String message){
        return new ResponseResult<T>(BasicCode.FAILED.getCode(), message, null);
    }


    public static <T> ResponseResult<T> failed(ErrorCode errorCode){
        return new ResponseResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }
}
