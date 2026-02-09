//package com.scrumsys.policy.client;
//
//import com.scrumsys.common.dto.ApiResponse;
//import com.scrumsys.common.dto.TokenValidationResponse;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//
//@FeignClient(
//        name = "auth-service",
//        url = "${auth.service.url:http://localhost:8087}"
//)
//public interface AuthServiceClient {
//
//    @PostMapping("/api/v1/auth/validate")
//    ApiResponse<TokenValidationResponse> validateToken(
//            @RequestHeader("Authorization") String authorizationHeader,
//            @RequestHeader("X-Client-ID") String clientId
//    );
//}



