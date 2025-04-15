package com.a5.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Custom error controller that handles all errors in the application.
 * Implements Spring Boot's ErrorController interface to provide custom error handling.
 * Renders different error pages based on the HTTP status code.
 */
@Controller
public class CustomErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    /**
     * Handles all error requests forwarded to /error.
     * Determines the appropriate error view based on the HTTP status code.
     * Logs error details at appropriate levels based on the error severity.
     * 
     * @param request The HTTP request that resulted in an error
     * @return A ModelAndView object with the appropriate error view and attributes
     */
    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        // Extract error details from the request attributes
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object errorException = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        Object requestUri = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

        // Default to internal server error
        int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String viewName = "error/500";
        
        if (status != null) {
            statusCode = Integer.parseInt(status.toString());
            
            // Handle different status codes
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                viewName = "error/404";
                logger.warn("404 Error: Requested resource not found - {}", requestUri);
            } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                viewName = "error/400";
                logger.warn("400 Error: Bad request - {}", requestUri);
            } else {
                // Log other errors as errors with more details
                logger.error("{} Error: {} - {}", statusCode, errorMessage, requestUri, errorException);
            }
        }

        // Create and populate the model and view
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("statusCode", statusCode);
        modelAndView.addObject("errorMessage", errorMessage);
        modelAndView.addObject("requestUri", requestUri);
        return modelAndView;
    }
} 