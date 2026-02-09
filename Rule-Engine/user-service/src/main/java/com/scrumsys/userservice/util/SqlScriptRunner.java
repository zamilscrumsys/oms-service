package com.scrumsys.userservice.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import javax.sql.DataSource;
import java.sql.Connection;

@Slf4j
@Configuration
public class SqlScriptRunner {

    @Bean
    public CommandLineRunner loadDatabase(DataSource dataSource) {
        return args -> {
            try {
                log.info("Starting database initialization for User Service...");

                // Run data.sql
                try {
                    ClassPathResource dataResource = new ClassPathResource("data.sql");
                    if (dataResource.exists()) {
                        log.info("Found data.sql, executing...");

                        // Use try-with-resources to ensure connection is closed
                        try (Connection connection = dataSource.getConnection()) {
                            DatabasePopulator dataPopulator = new ResourceDatabasePopulator(dataResource);
                            dataPopulator.populate(connection);
                            log.info("data.sql executed successfully");
                        }
                    } else {
                        log.warn("data.sql not found in classpath");
                    }
                } catch (Exception e) {
                    log.error("Error executing data.sql: {}", e.getMessage());
                    // Log the SQL error more specifically
                    if (e.getCause() != null) {
                        log.error("Root cause: {}", e.getCause().getMessage());
                    }
                }

                log.info("User Service database initialization completed!");
                log.info("==============================================");
                log.info("DEFAULT USER CREDENTIALS:");
                log.info("Admin: mohamedzamil124@gmail.com / Admin@123");
                log.info("User: john.doe@example.com / User@123");
                log.info("==============================================");

            } catch (Exception e) {
                log.error("Error during database initialization: ", e);
            }
        };
    }
}