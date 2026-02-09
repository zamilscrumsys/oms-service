package com.scrumsys.common.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final String errorCode;
    private final int httpStatus;

    public ApplicationException(String message, String errorCode, int httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public ApplicationException(String message, String errorCode, int httpStatus, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public static class NotFoundException extends ApplicationException {
        public NotFoundException(String message) {
            super(message, "NOT_FOUND", 404);
        }
    }

    public static class BadRequestException extends ApplicationException {
        public BadRequestException(String message) {
            super(message, "BAD_REQUEST", 400);
        }
    }

    public static class UnauthorizedException extends ApplicationException {
        public UnauthorizedException(String message) {
            super(message, "UNAUTHORIZED", 401);
        }
    }

    public static class ForbiddenException extends ApplicationException {
        public ForbiddenException(String message) {
            super(message, "FORBIDDEN", 403);
        }
    }

    public static class ConflictException extends ApplicationException {
        public ConflictException(String message) {
            super(message, "CONFLICT", 409);
        }
    }

    public static class ValidationException extends ApplicationException {
        public ValidationException(String message) {
            super(message, "VALIDATION_ERROR", 422);
        }
    }

    public static class InternalServerException extends ApplicationException {
        public InternalServerException(String message) {
            super(message, "INTERNAL_SERVER_ERROR", 500);
        }
    }
}


//last commented code
// package com.scrumsys.common.exception;
//
//import org.springframework.http.HttpStatus;
//
//public class ApplicationException extends RuntimeException {
//    private static final long serialVersionUID = 1L;
//    private final HttpStatus status;
//    private final ErrorCode errorCode;
//    private final String context;
//
//    public ApplicationException(HttpStatus status, ErrorCode errorCode, String context) {
//        super(context);
//        this.status = status;
//        this.errorCode = errorCode;
//        this.context = context;
//    }
//
//    public ApplicationException(HttpStatus status, ErrorCode errorCode, String context, Throwable cause) {
//        super(context, cause);
//        this.status = status;
//        this.errorCode = errorCode;
//        this.context = context;
//    }
//
//    public HttpStatus getStatus() {
//        return status;
//    }
//
//    public ErrorCode getErrorCode() {
//        return errorCode;
//    }
//
//    @Override
//    public String getMessage() {
//        return context;
//    }
//}


//// default code
//package com.scrumsys.common.exception;
//
//import lombok.*;
//
//import java.util.MissingResourceException;
//import java.util.ResourceBundle;
//
//
//public class ApplicationException extends RuntimeException {
//
//    private static final long serialVersionUID = 1L;
//    private static final String RESOURCE_BUNDLE = "errorMessages";
//    private String exceptionCode;
//    private String exceptionContext;
//    private String exceptionSource;
//
//    public ApplicationException(Throwable cause) {
//        super(cause);
//        this.initCause(cause);
//    }
//
//    public ApplicationException(String message) {
//        super(message);
//    }
//
//    public ApplicationException(String code, String context) {
//        super();
//        this.exceptionCode = code;
//        this.exceptionContext = context;
//    }
//
//    public ApplicationException(String code, String context, Throwable cause) {
//        this(code, context);
//        this.initCause(cause);
//    }
//
//    public String getExceptionCode() {
//        return exceptionCode;
//    }
//
//    public String getExceptionContext() {
//        return exceptionContext;
//    }
//
//    public void setExceptionSource(String src) {
//        this.exceptionSource = src;
//    }
//
//    @Override
//    public String getMessage() {
//        return exceptionContext == null ? String.format("[%1$s]", exceptionCode) : String.format("[%1$s] %2$s", exceptionCode, exceptionContext);
//    }
//
//    public String getLocalizedMessage() {
//        String msg = getMessageByCode();
//        if (msg == null || msg.isBlank()) {
//            return exceptionContext == null ? exceptionCode : exceptionContext;
//        }
//        return exceptionContext == null ? msg : ("[" + exceptionCode + "], " + msg);
//    }
//
//    private String getMessageByCode() {
//        try {
//            return ResourceBundle.getBundle(RESOURCE_BUNDLE, java.util.Locale.US).getString(exceptionCode);
//        } catch (MissingResourceException e) {
//            return exceptionCode;
//        }
//    }
//}
