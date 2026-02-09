package com.scrumsys.common.config.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("Feign client error for method: {}, status: {}",
                methodKey, response.status());

        return new RuntimeException(
                String.format("Feign client error: %s", response.reason())
        );
    }
}

























//import com.scrumsys.common.exception.ApplicationException;
//import feign.Response;
//import feign.codec.ErrorDecoder;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class FeignErrorDecoder implements ErrorDecoder {
//
//    @Override
//    public Exception decode(String methodKey, Response response) {
//        log.error("Feign client error - Method: {}, Status: {}", methodKey, response.status());
//
//        if (response.status() == 404) {
//            return new ApplicationException.NotFoundException("User not found");
//        } else if (response.status() == 401) {
//            return new ApplicationException.UnauthorizedException("Invalid credentials");
//        } else if (response.status() >= 500) {
//            return new ApplicationException.InternalServerException("User service error");
//        }
//
//        return new Exception("Feign client error: " + response.status());
//    }
//}
