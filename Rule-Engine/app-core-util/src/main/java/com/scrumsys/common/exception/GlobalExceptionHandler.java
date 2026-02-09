
//package com.scrumsys.common.exception;
//
//import com.scrumsys.common.dto.ResponseDTO;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//
//import java.time.Instant;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(ApplicationException.class)
//    public ResponseEntity<ResponseDTO<Map<String, Object>>> handleApplicationException(
//            ApplicationException ex, WebRequest request) {
//
//        Map<String, Object> errorBody = new LinkedHashMap<>();
//        errorBody.put("timestamp", Instant.now());
//        errorBody.put("status", ex.getStatus().value());
//        errorBody.put("error", ex.getStatus().getReasonPhrase());
//        errorBody.put("code", ex.getErrorCode().name());
//        errorBody.put("message", ex.getMessage());
//        errorBody.put("path", request.getDescription(false).replace("uri=", ""));
//
//        ResponseDTO<Map<String, Object>> response = ResponseDTO.failure(ex.getMessage(), errorBody);
//
//        return ResponseEntity
//                .status(ex.getStatus())
//                .body(response);
//    }
//
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<ResponseDTO<Map<String, Object>>> handleNotFoundException(
//            NotFoundException ex, WebRequest request) {
//
//        Map<String, Object> errorBody = new LinkedHashMap<>();
//        errorBody.put("timestamp", Instant.now());
//        errorBody.put("status", HttpStatus.NOT_FOUND.value());
//        errorBody.put("error", "Not Found");
//        errorBody.put("message", ex.getMessage());
//        errorBody.put("path", request.getDescription(false).replace("uri=", ""));
//
//        ResponseDTO<Map<String, Object>> response = ResponseDTO.failure(errorBody);
//
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(response);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, Object>> handleAnyException(
//            Exception ex, WebRequest request) {
//
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("timestamp", Instant.now());
//        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        body.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
//        body.put("message", ex.getMessage());
//        body.put("path", request.getDescription(false).replace("uri=", ""));
//
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(body);
//    }
//}







//package com.scrumsys.common.exception;
//
//import com.scrumsys.common.dto.ResponseDTO;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import java.time.Instant;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(ApplicationException.class)
//    public ResponseEntity<ResponseDTO<Map<String, Object>>> handleApplicationException(
//            ApplicationException ex) {
//
//        Map<String, Object> errorBody = new LinkedHashMap<>();
//        errorBody.put("timestamp", Instant.now());
//        errorBody.put("code", ex.getExceptionCode());
//        errorBody.put("message", ex.getLocalizedMessage());
//
//        ResponseDTO<Map<String, Object>> response = ResponseDTO.failure(
//                "Application Error",
//                errorBody
//        );
//
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(response);
//    }
//
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<ResponseDTO<Map<String, Object>>> handleAuthenticationException(
//            AuthenticationException ex) {
//
//        Map<String, Object> errorBody = new LinkedHashMap<>();
//        errorBody.put("timestamp", Instant.now());
//        errorBody.put("error", "AUTHENTICATION_FAILED");
//        errorBody.put("message", ex.getMessage());
//
//        ResponseDTO<Map<String, Object>> response = ResponseDTO.failure(
//                "Authentication Error",
//                errorBody
//        );
//
//        return ResponseEntity
//                .status(HttpStatus.UNAUTHORIZED)
//                .body(response);
//    }
//
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<ResponseDTO<Map<String, Object>>> handleNotFoundException(
//            NotFoundException ex) {
//
//        Map<String, Object> errorBody = new LinkedHashMap<>();
//        errorBody.put("timestamp", Instant.now());
//        errorBody.put("error", "NOT_FOUND");
//        errorBody.put("message", ex.getMessage());
//
//        ResponseDTO<Map<String, Object>> response = ResponseDTO.failure(
//                "Resource Not Found",
//                errorBody
//        );
//
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(response);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, Object>> handleAnyException(Exception ex) {
//
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("timestamp", Instant.now());
//        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        body.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
//        body.put("message", ex.getMessage());
//
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(body);
//    }
//}

// default code
package com.scrumsys.common.exception;

import com.scrumsys.common.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;



@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ResponseDTO<Object>> handleAppException(
            ApplicationException ex) {

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(
                        ResponseDTO.builder()
                                .success(false)
                                .errorCode(ex.getErrorCode())
                                .message(ex.getMessage())
                                //.timestamp(Instant.now())
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<Object>> handleOtherExceptions(
            Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ResponseDTO.builder()
                                .success(false)
                                .errorCode("INTERNAL_ERROR")
                                .message("Unexpected server error")
                                // .timestamp(Instant.now())
                                .build()
                );
    }
}





// default code

//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(ApplicationException.class)
//    public ResponseEntity<ResponseDTO<Map<String, Object>>> handleApplicationException(
//            ApplicationException ex) {
//
//        Map<String, Object> errorBody = new LinkedHashMap<>();
//        errorBody.put("timestamp", Instant.now());
//        errorBody.put("status", ex.getStatus().value());
//        errorBody.put("error", ex.getStatus().getReasonPhrase());
//        errorBody.put("code", ex.getErrorCode().name());
//        errorBody.put("message", ex.getMessage());
//
//        ResponseDTO<Map<String, Object>> response =
//                ResponseDTO.failure(errorBody);
//
//        return ResponseEntity
//                .status(ex.getStatus())
//                .body(response);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, Object>> handleAny(Exception ex) {
//
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("timestamp", Instant.now());
//        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        body.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
//        body.put("message", ex.getMessage());
//
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(body);
//    }


