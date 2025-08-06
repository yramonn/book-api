package com.f1rst.bookapi.services.impl;

import com.f1rst.bookapi.dtos.BookByGenderDTO;
import com.f1rst.bookapi.dtos.BookDTO;
import com.f1rst.bookapi.dtos.OpenLibraryByKeyResponseDTO;
import com.f1rst.bookapi.exceptions.UserNotFoundException;
import com.f1rst.bookapi.models.BookRecentlyViewModel;
import com.f1rst.bookapi.repositories.BookRecentlyViewRepository;
import com.f1rst.bookapi.services.BookRecentlyViewService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public void saveRecentlyViewedBookById(OpenLibraryByKeyResponseDTO book, String userId) {
        var bookRecentlyViewModel = new BookRecentlyViewModel();
        bookRecentlyViewModel.setUserId(userId);
        bookRecentlyViewModel.setBookKey(book.getId());
        bookRecentlyViewModel.setTitle(book.getTitle());
        bookRecentlyViewModel.setViewedAt(LocalDateTime.now());
        bookRecentlyViewRepository.save(bookRecentlyViewModel);
    }

    @Override
    public void saveRecentlyViewedBookByGender(List<BookByGenderDTO> books, String userId) {
        List<BookRecentlyViewModel> entities = books.stream()
                .map(dto -> BookRecentlyViewModel.buildEntity(dto, userId))
                .toList();
        bookRecentlyViewRepository.saveAll(entities);

    }

    @Override
    public List<BookRecentlyViewModel> getRecentlyViewedBooks(String userId) {
        List<BookRecentlyViewModel> books = bookRecentlyViewRepository.findTop10ByUserIdOrderByViewedAtDesc(userId);
        if(books.isEmpty()) {
            throw new UserNotFoundException(userId);
        }
        return books;
    }
}
