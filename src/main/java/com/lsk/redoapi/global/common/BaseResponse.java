package com.lsk.redoapi.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

    private int status;
    private String message;
    private T data;

    private static <T> ResponseEntity<BaseResponse<T>> response(HttpStatus httpStatus, String message, T data) {
        return ResponseEntity.status(httpStatus)
                .body(new BaseResponse<>(httpStatus.value(), message, data));
    }

    public static <T> ResponseEntity<BaseResponse<T>> ok(T data) {
        return response(HttpStatus.OK, "Success", data);
    }

    public static <T> ResponseEntity<BaseResponse<T>> ok(String message, T data) {
        return response(HttpStatus.OK, message, data);
    }

    public static <T> ResponseEntity<BaseResponse<T>> created(T data) {
        return response(HttpStatus.CREATED, "Created", data);
    }

    public static <T> ResponseEntity<BaseResponse<T>> created(String message, T data) {
        return response(HttpStatus.CREATED, message, data);
    }

    public static ResponseEntity<BaseResponse<Void>> noContent() {
        return response(HttpStatus.NO_CONTENT, "No Content", null);
    }

    public static ResponseEntity<BaseResponse<Void>> noContent(String message) {
        return response(HttpStatus.NO_CONTENT, message, null);
    }

    public static <T> ResponseEntity<BaseResponse<T>> error(int status, String message) {
        return ResponseEntity.status(status)
                .body(new BaseResponse<>(status, message, null));
    }

    public static <T> ResponseEntity<BaseResponse<T>> error(int status, String message, T data) {
        return ResponseEntity.status(status)
                .body(new BaseResponse<>(status, message, data));
    }
}
