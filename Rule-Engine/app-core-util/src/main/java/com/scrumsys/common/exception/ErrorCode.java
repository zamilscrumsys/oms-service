package com.scrumsys.common.exception;

/**
 * Centralised error codes for application-level failures.
 * Add new codes here and map them in error message bundles if needed.
 */
public enum ErrorCode {
    INVALID_INPUT,
    NOT_FOUND,
    UNAUTHORIZED,
    FORBIDDEN,
    CONFLICT,
    INTERNAL_ERROR,
    VALIDATION_FAILED,
    RESOURCE_LOCKED
}