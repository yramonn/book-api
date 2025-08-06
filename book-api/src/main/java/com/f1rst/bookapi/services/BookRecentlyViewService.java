package com.f1rst.bookapi.services;

import com.f1rst.bookapi.dtos.BookByGenderDTO;
import com.f1rst.bookapi.dtos.BookDTO;
import com.f1rst.bookapi.dtos.OpenLibraryByKeyResponseDTO;
import com.f1rst.bookapi.models.BookRecentlyViewModel;

import java.util.List;

public interface BookRecentlyViewService {
    void saveRecentlyViewedBook(List<BookDTO> books, String userId);
    void saveRecentlyViewedBookById(OpenLibraryByKeyResponseDTO book, String userId);
    void saveRecentlyViewedBookByGender(List<BookByGenderDTO> books, String userId);
    List<BookRecentlyViewModel> getRecentlyViewedBooks(String userId);

}
