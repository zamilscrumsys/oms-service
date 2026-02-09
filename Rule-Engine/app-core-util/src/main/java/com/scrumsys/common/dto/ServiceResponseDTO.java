package com.scrumsys.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResponseDTO<T> {
    private boolean success;
    private String message;
    private String code;
    private Instant timestamp;
    private T data;

    public static <T> ServiceResponseDTO<T> success(T data) {
        return ServiceResponseDTO.<T>builder()
                .success(true)
                .message("Operation successful")
                .timestamp(Instant.now())
                .data(data)
                .build();
    }

    public static <T> ServiceResponseDTO<T> success(String message, T data) {
        return ServiceResponseDTO.<T>builder()
                .success(true)
                .message(message)
                .timestamp(Instant.now())
                .data(data)
                .build();
    }

    public static <T> ServiceResponseDTO<T> error(String message, String code) {
        return ServiceResponseDTO.<T>builder()
                .success(false)
                .message(message)
                .code(code)
                .timestamp(Instant.now())
                .build();
    }
}
