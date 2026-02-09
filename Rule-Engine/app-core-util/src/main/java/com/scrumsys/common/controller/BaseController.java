package com.scrumsys.common.controller;

import com.scrumsys.common.dto.ApiResponse;
import com.scrumsys.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseController {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiResponse<Object>> handleApplicationException(ApplicationException ex) {
        ApiResponse<Object> response = ApiResponse.error(
                ex.getMessage(),
                String.valueOf(ex.getHttpStatus())
        );
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getHttpStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
        ApiResponse<Object> response = ApiResponse.error(
                "An unexpected error occurred",
                "500"
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}