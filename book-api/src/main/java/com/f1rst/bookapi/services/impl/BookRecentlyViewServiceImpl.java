package com.f1rst.bookapi.services.impl;

import com.f1rst.bookapi.dtos.BookDTO;
import com.f1rst.bookapi.models.BookRecentlyViewModel;
import com.f1rst.bookapi.repositories.BookRecentlyViewRepository;
import com.f1rst.bookapi.services.BookRecentlyViewService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookRecentlyViewServiceImpl implements BookRecentlyViewService {

    final BookRecentlyViewRepository bookRecentlyViewRepository;

    public BookRecentlyViewServiceImpl(BookRecentlyViewRepository bookRecentlyViewRepository) {
        this.bookRecentlyViewRepository = bookRecentlyViewRepository;
    }

    @Override
    public void saveRecentlyViewedBook(List<BookDTO> books, String userId) {
        List<BookRecentlyViewModel> entities = books.stream()
                .map(dto -> BookRecentlyViewModel.buildEntity(dto, userId))
                .toList();
        bookRecentlyViewRepository.saveAll(entities);
    }

    @Override
    public List<BookRecentlyViewModel> getRecentlyViewedBooks(String userId) {
        return bookRecentlyViewRepository.findTop10ByUserIdOrderByViewedAtDesc(userId);
    }
}
