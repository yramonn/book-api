package com.f1rst.bookapi.services;

import com.f1rst.bookapi.dtos.BookByGenderDTO;
import com.f1rst.bookapi.dtos.BookDTO;
import com.f1rst.bookapi.dtos.OpenLibraryByKeyResponseDTO;

import java.util.List;

public interface BookService {

    List<BookDTO> getAllBooks(String q, int page, int limit, String userId);

    List<BookDTO> getAllBooksByAuthor(String author, int page, int limit, String userId);

    OpenLibraryByKeyResponseDTO getBookById(String id, String userId);

    List<BookByGenderDTO> gelAllBooksByGender(String gender, int page, int limit, String userId);
}
