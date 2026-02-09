//package com.scrumsys.common.config;
//
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JwtTokenExtractor {
//
//    public String getJwtFromSecurityContext() {
//        try {
//            Object authDetails = SecurityContextHolder.getContext()
//                    .getAuthentication()
//                    .getCredentials();
//
//            if (authDetails instanceof String token) {
//                return token;
//            }
//        } catch (Exception ignored) {
//        }
//
//        return null;
//    }
//}


//package com.scrumsys.common.config;
//
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JwtTokenExtractor {
//
//    public String getJwtFromSecurityContext() {
//        try {
//            Object authDetails = SecurityContextHolder.getContext()
//                    .getAuthentication()
//                    .getCredentials();
//
//            if (authDetails instanceof String token) {
//                return token; // JWT
//            }
//
//        } catch (Exception ignored) {}
//
//        return null; // No token found
//    }
//}