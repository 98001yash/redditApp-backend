package com.redditApp.feed_service.exceptions;

public class RuntimeConflictException extends RuntimeException{
    public RuntimeConflictException() {
    }

    public RuntimeConflictException(String message) {
        super(message);
    }
}

