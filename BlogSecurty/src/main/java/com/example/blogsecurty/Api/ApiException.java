package com.example.blogsecurty.Api;

import lombok.Data;


public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
