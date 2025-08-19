package com.f1rst.bookapi.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String userId) {
        super("Books not found for UserId " + userId);
    }
}
