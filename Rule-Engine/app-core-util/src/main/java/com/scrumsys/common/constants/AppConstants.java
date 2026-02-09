package com.scrumsys.common.constants;

public class AppConstants {

    // JWT Constants
    public static final String JWT_SECRET = "c3ByaW5nLWJvb3Qtc2VjdXJpdHktand0LXR1dG9yaWFsLWppdmF0b2RvZGV2LXNwcmluZy1ib290LXNlY3VyaXR5LWp3dC10dXRvcmlhbA==";
    public static final long JWT_ACCESS_TOKEN_EXPIRATION = 900000; // 15 minutes in milliseconds
    public static final long JWT_REFRESH_TOKEN_EXPIRATION = 604800000; // 7 days in milliseconds

    // Token Prefix
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CLIENT_ID_HEADER = "X-Client-ID";
    public static final String CLIENT_SECRET_HEADER = "X-Client-Secret";

    // User Roles
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";

    // Login Types
    public static final String LOGIN_TYPE_LOCAL = "LOCAL";
    public static final String LOGIN_TYPE_GOOGLE = "GOOGLE";
    public static final String LOGIN_TYPE_MOBILE = "MOBILE";

    // OTP Constants
    public static final int OTP_LENGTH = 6;
    public static final int OTP_EXPIRY_MINUTES = 5;

    // Response Messages
    public static final String SUCCESS_MESSAGE = "Success";
    public static final String ERROR_MESSAGE = "Error";

    // Email Constants
    public static final String EMAIL_SUBJECT_OTP = "Your OTP Code";
    public static final String EMAIL_SUBJECT_PASSWORD_RESET = "Password Reset Request";

    // Client Types
    public static final String CLIENT_TYPE_WEB = "WEB";
    public static final String CLIENT_TYPE_MOBILE = "MOBILE";

    // Pagination
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final  String ACTIVE_STATUS = "A";
    public static     String DELETED_STATUS = "D";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";
   public static final  int VERSION_ONE = 1;


}








//package com.scrumsys.common.constants;
//
//public interface AppConstants {
//    String LOGGED_IN_USER = "loginUser";
//    String LOGIN_ID = "loginId";
//    int VERSION_ONE = 1;
//    String ACTIVE_STATUS = "A";
//    String ADMIN_ROLE = "ADMIN";
//    String USER_ROLE = "USER";
//    String URL_ADD = "ADD";
//    String URL_RPT_DWLN = "/reportDownload/searchByCriteria";
//    String PROCESS_ZERO = "0";
//    String AUTH_ERROR_MSG = "authErrMsg";
//    String WIN_OS_NAME = "Windows";
//    String SH_EXTN = ".sh";
//    String BAT_EXTN = ".bat";
//    String SELECT_NONE = "0";
//    String HOME_URL = "/form";
//    String LOGIN_AUTH_URL = "/auth/login";
//    String RESOURCES_URL = "/resources/";
//    String ENV_ID = "envID";
//    String DELETED_STATUS = "D";
//    String LOCAL_LOGIN = "LOCAL";
//    String GOOGLE_LOGIN = "GOOGLE";
//
//     Auth specific constants
//    String TOKEN_TYPE_BEARER = "Bearer";
//    String PROVIDER_NATIVE = "native";
//    String PROVIDER_GOOGLE = "google";
//    String OTP_CHANNEL_EMAIL = "EMAIL";
//    String OTP_CHANNEL_MOBILE = "MOBILE";
//
//    // Error messages
//    String ERROR_USER_NOT_FOUND = "User not found";
//    String ERROR_INVALID_CREDENTIALS = "Invalid credentials";
//    String ERROR_INVALID_TOKEN = "Invalid or expired token";
//    String ERROR_INVALID_OTP = "Invalid or expired OTP";
//    String ERROR_USER_EXISTS = "User already exists";
//    String ERROR_UNAUTHORIZED = "Unauthorized access";
//    String ERROR_FORBIDDEN = "Forbidden access";
//
//     Response messages
//    String MSG_LOGIN_SUCCESS = "Login successful";
//    String MSG_REFRESH_SUCCESS = "Token refreshed successfully";
//    String MSG_LOGOUT_SUCCESS = "Logout successful";
//    String MSG_OTP_SENT = "OTP sent successfully";
//    String MSG_OTP_VERIFIED = "OTP verified successfully";
//
//    String USER_ID = "userId";
//    String EMAIL = "email";
//    String PHONE = "phone";
//    String ROLES = "roles";
//    String FUNCTIONS = "functions";
//    String DEPARTMENT = "department";
//
//}