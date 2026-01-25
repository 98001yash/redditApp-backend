package com.redditApp.post_service.exceptions;

public class RuntimeConflictException extends RuntimeException{
    public RuntimeConflictException() {
    }

    public RuntimeConflictException(String message) {
        super(message);
    }
}

