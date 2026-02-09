package com.scrumsys.common.constants;


public class AuthConstants {
    private AuthConstants() {}

    // JWT Claims
    public static final String CLAIM_ROLES = "roles";
    public static final String CLAIM_PERMISSIONS = "permissions";
    public static final String CLAIM_TOKEN_VERSION = "tokenVersion";
    public static final String CLAIM_USER_ID = "userId";

    // Headers
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_AUTH_USER = "X-Auth-User";
    public static final String HEADER_SERVICE_REQUEST = "X-Service-Request";

    // Paths
    public static final String PATH_AUTH = "/api/v1/auth";
    public static final String PATH_USER = "/api/v1/user";
    public static final String PATH_ADMIN = "/api/v1/admin";

    // Token
    public static final long ACCESS_TOKEN_VALIDITY = 900; // 15 minutes in seconds
    public static final long REFRESH_TOKEN_VALIDITY = 2592000; // 30 days in seconds
    public static final int OTP_LENGTH = 6;
    public static final int OTP_EXPIRY_MINUTES = 5;
}