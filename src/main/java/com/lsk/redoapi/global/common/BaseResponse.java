package com.lsk.redoapi.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

    private int status;
    private String message;
    private T data;

    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<>(200, "Success", data);
    }

    public static <T> BaseResponse<T> ok(String message, T data) {
        return new BaseResponse<>(200, message, data);
    }

    public static <T> BaseResponse<T> created(T data) {
        return new BaseResponse<>(201, "Created", data);
    }

    public static <T> BaseResponse<T> created(String message, T data) {
        return new BaseResponse<>(201, message, data);
    }

    public static BaseResponse<Void> noContent() {
        return new BaseResponse<>(204, "No Content", null);
    }

    public static BaseResponse<Void> noContent(String message) {
        return new BaseResponse<>(204, message, null);
    }

    public static <T> BaseResponse<T> error(int status, String message) {
        return new BaseResponse<>(status, message, null);
    }

    public static <T> BaseResponse<T> error(int status, String message, T data) {
        return new BaseResponse<>(status, message, data);
    }
}
