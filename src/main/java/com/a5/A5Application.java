package com.a5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main application class for the Student Course Management System.
 * This is the entry point for the Spring Boot application.
 * 
 * - @SpringBootApplication: Enables Spring Boot auto-configuration and component scanning
 * - @EntityScan: Specifies where to find JPA entity classes
 * - @EnableJpaRepositories: Specifies where to find repository interfaces
 */
@SpringBootApplication(scanBasePackages = "com.a5")
@EntityScan("com.a5.model")
@EnableJpaRepositories("com.a5.repository")
public class A5Application {

    /**
     * Main method that starts the Spring Boot application.
     * 
     * @param args Command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(A5Application.class, args);
    }
} 