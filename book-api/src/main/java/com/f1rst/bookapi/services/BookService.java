package com.f1rst.bookapi.services;

import com.f1rst.bookapi.dtos.BookDTO;

import java.util.List;

public interface BookService {

    List<BookDTO> getAllBooks(String q, int page, int limit, String userId);

    List<BookDTO> getAllBooksByAuthor(String author, int page, int limit, String userId);

    List<BookDTO> getBookById(String id, String userId);

    List<BookDTO> gelAllBooksByGender(String gender, int page, int limit, String userId);
}
