package com.scrumsys.common.client;


import com.scrumsys.common.dto.ApiResponse;
import com.scrumsys.common.dto.TokenValidationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name="auth-service",url = "http://localhost:8087")
public interface AuthFeignClient {

    @PostMapping("/api/v1/auth/validate-refresh")
    void validateRefresh(
            @RequestHeader("X-CLIENT-CREDENTIAL") String encodedClient,
            @RequestHeader("X-Refresh-Token") String refreshToken);


//    @PostMapping("/api/v1/auth/validate-refresh")
//    ApiResponse<TokenValidationResponse> validateRefresh(
//            @RequestHeader("X-CLIENT-CREDENTIAL") String encodedClient,
//            @RequestHeader("X-Refresh-Token") String refreshToken
//    );


//@GetMapping("/api/v1/auth/user-info")
//String getUsernameFromToken(@RequestHeader("Authorization") String authorizationHeader);

//@PostMapping("/api/v1/auth/account-created")
//void sendAccountCreatedEmail(
//        @RequestParam String email,
//        @RequestParam String tempPassword
//);

}
