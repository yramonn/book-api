package com.f1rst.bookapi.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("UserId " + userId + " not found.");
    }
}
