package com.example.blogsecurty.ControllerAdviser;

import com.example.blogsecurty.Api.ApiException;
import com.example.blogsecurty.Api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class ControllerAdvise {

    // Custom API Exception
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ApiResponse> handleApiException(ApiException e) {
        return ResponseEntity.status(400).body(new ApiResponse(e.getMessage()));
    }

    // Validation Exceptions
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getFieldError().getDefaultMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    // SQL Constraint Exception
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleSQLIntegrityException(SQLIntegrityConstraintViolationException e) {
        return ResponseEntity.status(400).body(new ApiResponse("Database error: " + e.getMessage()));
    }

    // Unsupported HTTP Method Exception
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(405).body(new ApiResponse("HTTP method not allowed: " + e.getMessage()));
    }

    // JSON Parsing Exception
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleJsonParsingException(HttpMessageNotReadableException e) {
        return ResponseEntity.status(400).body(new ApiResponse("Malformed JSON request: " + e.getMessage()));
    }

    // Argument Type Mismatch Exception
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(400).body(new ApiResponse("Invalid parameter type: " + e.getMessage()));
    }
}
