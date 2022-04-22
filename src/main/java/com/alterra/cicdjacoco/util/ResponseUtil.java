package com.alterra.cicdjacoco.util;


import com.alterra.cicdjacoco.domain.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ResponseUtil {

    private ResponseUtil(){}

    public static <T> ResponseEntity<Object> build(String message, T data, HttpStatus httpStatus){
        return new ResponseEntity<>(build(message,data), httpStatus);
    }

    private static <T> ApiResponse<T> build(String message, T data) {
        return ApiResponse.<T>builder()

                .localDateTime(LocalDateTime.now())
                .message(message)
                .data(data)
                .build();
    }
}
