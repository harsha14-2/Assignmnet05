package com.a5.exception;

/**
 * Exception thrown when a requested resource is not found.
 * Extends RuntimeException to be unchecked, allowing for cleaner service code.
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Constructs a new ResourceNotFoundException with a formatted message.
     * 
     * @param resourceName The type of resource that was not found (e.g., "Student", "Course")
     * @param fieldName The field name used in the lookup (e.g., "id", "email")
     * @param fieldValue The value of the field that was used in the lookup
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
} 