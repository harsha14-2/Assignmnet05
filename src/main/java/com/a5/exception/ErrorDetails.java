package com.a5.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Class for representing error details in a consistent format.
 * Used for conveying error information in API responses.
 */
@Data
@NoArgsConstructor
public class ErrorDetails {
    /**
     * The time when the error occurred.
     */
    private LocalDateTime timestamp;
    
    /**
     * A descriptive message about the error.
     */
    private String message;
    
    /**
     * The path/URI that triggered the error.
     */
    private String path;
    
    /**
     * A code that identifies the type of error.
     */
    private String errorCode;
    
    /**
     * Additional details about validation errors.
     * Maps field names to error messages.
     */
    private Map<String, String> errors;

    /**
     * Constructor for simple errors without validation details.
     * 
     * @param timestamp The time when the error occurred
     * @param message A descriptive message about the error
     * @param path The path/URI that triggered the error
     * @param errorCode A code that identifies the type of error
     */
    public ErrorDetails(LocalDateTime timestamp, String message, String path, String errorCode) {
        this.timestamp = timestamp;
        this.message = message;
        this.path = path;
        this.errorCode = errorCode;
    }

    /**
     * Constructor for validation errors with field-specific details.
     * 
     * @param timestamp The time when the error occurred
     * @param message A descriptive message about the error
     * @param path The path/URI that triggered the error
     * @param errorCode A code that identifies the type of error
     * @param errors A map of field names to error messages
     */
    public ErrorDetails(LocalDateTime timestamp, String message, String path, String errorCode, Map<String, String> errors) {
        this.timestamp = timestamp;
        this.message = message;
        this.path = path;
        this.errorCode = errorCode;
        this.errors = errors;
    }
} 