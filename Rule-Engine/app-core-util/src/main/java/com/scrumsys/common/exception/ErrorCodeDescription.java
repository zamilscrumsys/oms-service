package com.scrumsys.common.exception;

public final class ErrorCodeDescription {
    private ErrorCodeDescription() {
    }

    public static String get(ErrorCode code) {
        switch (code) {
            case INVALID_INPUT:
                return "Invalid input provided";
            case NOT_FOUND:
                return "Requested resource not found";
            case UNAUTHORIZED:
                return "Unauthorized";
            case FORBIDDEN:
                return "Forbidden";
            case CONFLICT:
                return "Conflict occurred";
            case VALIDATION_FAILED:
                return "Validation failed";
            case RESOURCE_LOCKED:
                return "Resource locked or modified by another transaction";
            case INTERNAL_ERROR:
            default:
                return "Internal server error";
        }
    }
}