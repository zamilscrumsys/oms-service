package com.scrumsys.common.dto;




import lombok.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;
    private T data;
    private boolean success;

    private String errorCode;   // ✅ ADD THIS LINE

    // Success with message & data
    public static <T> ResponseDTO<T> success(String message, T data) {
        return ResponseDTO.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    // Success with only data (backward compatible)
    public static <T> ResponseDTO<T> success(T data) {
        return ResponseDTO.<T>builder()
                .success(true)
                .message("Success")
                .data(data)
                .build();
    }

    // Failure with only data (backward compatible)
    public static <T> ResponseDTO<T> failure(T data) {
        return ResponseDTO.<T>builder()
                .success(false)
                .data(data)
                .build();
    }

    // Failure with message & data
    public static <T> ResponseDTO<T> failure(String message, T data) {
        return ResponseDTO.<T>builder()
                .success(false)
                .message(message)
                .data(data)
                .build();
    }

    // ✅ NEW helper (optional but recommended)
    public static <T> ResponseDTO<T> failure(String errorCode, String message) {
        return ResponseDTO.<T>builder()
                .success(false)
                .errorCode(errorCode)
                .message(message)
                .build();
    }
}

