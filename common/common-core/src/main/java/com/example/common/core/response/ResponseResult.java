package com.example.common.core.response;


import com.example.common.core.response.code.BasicCode;
import com.example.common.core.response.code.ErrorCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class ResponseResult<T> {
    private int code;

    private String message;

    private T data;

    private long timestamp = System.currentTimeMillis();

    private ResponseResult(int code, String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 请求成功
     * @return
     * @param <T>
     */
    public static <T> ResponseResult<T> success(){
        return success(null);
    }

    public static <T> ResponseResult<T> success(T data){
        return new ResponseResult<>(BasicCode.SUCCESS.getCode(), BasicCode.SUCCESS.getMessage(), data);
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
        return new ResponseResult<>(BasicCode.FAILED.getCode(), message, null);
    }


    public static <T> ResponseResult<T> failed(ErrorCode errorCode){
        return new ResponseResult<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    @JsonIgnore
    public boolean isSuccess(){
        return this.getCode() == BasicCode.SUCCESS.getCode();
    }

    @JsonIgnore
    public boolean isFailed(){
        return !isSuccess();
    }
}
