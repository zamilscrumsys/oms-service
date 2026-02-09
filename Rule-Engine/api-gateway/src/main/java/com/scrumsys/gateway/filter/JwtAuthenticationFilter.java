package com.scrumsys.gateway.filter;

//import com.scrumsys.common.security.JwtUtil;
import com.scrumsys.gateway.security.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {


    private final JwtUtil jwtUtil;

    // Public endpoints
    private static final String[] WHITELIST = {
            "/api/auth/login",
            "/api/auth/refresh-token",
            "/api/auth/verify-otp",
            "/api/auth/forgot-password",
            "/api/auth/reset-password",
            "/api/oauth2",
            "/actuator",
            "/auth/google/login"
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // Allow whitelist paths
        if (isWhitelisted(path)) {
            return chain.filter(exchange);
        }

        // Read Authorization header
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtUtil.validateToken(token);
            String roles = claims.get("roles") != null ? claims.get("roles").toString() : "";

            // Mutate + propagate the request
            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(builder -> builder
                            .header("X-Auth-User", claims.getSubject())
                            .header("X-Auth-Roles", roles)
                    )
                    .build();

            return chain.filter(mutatedExchange);

        } catch (Exception e) {
            return unauthorized(exchange, "Invalid or expired JWT");
        }
    }

    private boolean isWhitelisted(String path) {
        for (String w : WHITELIST) {
            if (path.startsWith(w)) {
                return true;
            }
        }
        return false;
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1; // Run before Spring Security filter chain
    }
}