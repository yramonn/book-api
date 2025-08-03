package com.f1rst.bookapi.services;

import com.f1rst.bookapi.dtos.BookDTO;

import java.util.List;

public interface BookService {

    List<BookDTO> getAllBooks(String q, int page, int limit);

    List<BookDTO> getAllBooksByAuthor(String author, int page, int limit);

    List<BookDTO> getBookById(String id);

    List<BookDTO> gelAllBooksByGender(String gender, int page, int limit);
}
