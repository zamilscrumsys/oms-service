package com.scrumsys.policy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
@SpringBootApplication
@EnableFeignClients(basePackages = "com.scrumsys.common.client")
@ComponentScan(basePackages = {
        "com.scrumsys.policy",
        "com.scrumsys.common"
})
public class PolicyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PolicyServiceApplication.class, args);
    }
}
