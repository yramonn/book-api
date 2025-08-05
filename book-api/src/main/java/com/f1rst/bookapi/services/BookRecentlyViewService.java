package com.f1rst.bookapi.services;

import com.f1rst.bookapi.dtos.BookDTO;
import com.f1rst.bookapi.models.BookRecentlyViewModel;

import java.util.List;

public interface BookRecentlyViewService {
    public void saveRecentlyViewedBook(List<BookDTO> books, String userId);
    List<BookRecentlyViewModel> getRecentlyViewedBooks(String userId);

}
