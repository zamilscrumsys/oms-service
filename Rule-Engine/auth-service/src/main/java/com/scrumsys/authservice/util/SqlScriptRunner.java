//package com.scrumsys.authservice.util;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
//import org.springframework.jdbc.datasource.init.DatabasePopulator;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import javax.sql.DataSource;
//
//@Slf4j
//@Configuration
//public class SqlScriptRunner {
//
//    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//    @Bean
//    public CommandLineRunner loadDatabase(DataSource dataSource) {
//        return args -> {
//            try {
//                log.info("Starting database initialization for Auth Service...");
//
//
//                // Run data.sql
//                try {
//                    ClassPathResource dataResource = new ClassPathResource("data.sql");
//                    if (dataResource.exists()) {
//                        DatabasePopulator dataPopulator = new ResourceDatabasePopulator(dataResource);
//                        dataPopulator.populate(dataSource.getConnection());
//                        log.info("data.sql executed successfully");
//                    }
//                } catch (Exception e) {
//                    log.error("Error executing data.sql: {}", e.getMessage(), e);
//                }
//
//                log.info("Auth Service database initialization completed!");
//                log.info("==============================================");
//                log.info("DEFAULT CLIENT CREDENTIALS:");
//                log.info("Web Client ID: web_client_12345");
//                log.info("Web Client Secret: web_secret_67890");
//                log.info(encoder.encode("web_secret_67890"));
//                log.info("Mobile Client ID: mobile_client_12345");
//                log.info("Mobile Client Secret: mobile_secret_67890");
//                log.info("Google Client ID: google_client_12345");
//                log.info("Google Client Secret: google_secret_67890");
//                log.info(encoder.encode("google_secret_67890"));
//
//                log.info("==============================================");
//
//            } catch (Exception e) {
//                log.error("Error during database initialization: ", e);
//            }
//        };
//    }
//}
