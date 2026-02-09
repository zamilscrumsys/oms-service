package com.scrumsys.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.*;
import org.springframework.web.server.*;

@Configuration
public class CorsConfig {

    @Bean
    public WebFilter corsWebFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponse().getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            exchange.getResponse().getHeaders().add("Access-Control-Allow-Headers", "*");
            exchange.getResponse().getHeaders().add("Access-Control-Expose-Headers", "Authorization");
            exchange.getResponse().getHeaders().add("Access-Control-Allow-Credentials", "true");


            if (exchange.getRequest().getMethod().name().equals("OPTIONS")) {
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        };
    }
}
