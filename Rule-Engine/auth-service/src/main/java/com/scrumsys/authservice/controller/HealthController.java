package com.scrumsys.authservice.controller;



import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Auth Service is running");
    }

    @PostMapping("/test-post")
    public ResponseEntity<String> testPost(@RequestBody String testData) {
        log.info("Received POST data: {}", testData);
        return ResponseEntity.ok("POST received: " + testData);
    }
}
