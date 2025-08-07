package com.f1rst.bookapi.services;// src/test/java/com/f1rst/bookapi/services/impl/BookRecentlyViewServiceImplTest.java
import com.f1rst.bookapi.dtos.BookByGenderDTO;
import com.f1rst.bookapi.dtos.BookDTO;
import com.f1rst.bookapi.dtos.OpenLibraryByKeyResponseDTO;
import com.f1rst.bookapi.exceptions.UserNotFoundException;
import com.f1rst.bookapi.models.BookRecentlyViewModel;
import com.f1rst.bookapi.repositories.BookRecentlyViewRepository;
import com.f1rst.bookapi.services.impl.BookRecentlyViewServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookRecentlyViewServiceImplTest {

    @Mock
    BookRecentlyViewRepository bookRecentlyViewRepository;

    @InjectMocks
    BookRecentlyViewServiceImpl service;

    @DisplayName("saveRecentlyViewedBook should persist all provided books for user")
    @Test
    void saveRecentlyViewedBookPersistsEntities() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId("book-123");
        bookDTO.setTitle("Clean Code");
        bookDTO.setAuthorNames(List.of("Robert C. Martin"));
        String userId = "user1";
        service.saveRecentlyViewedBook(List.of(bookDTO), userId);
        verify(bookRecentlyViewRepository).saveAll(anyList());
    }

    @DisplayName("saveRecentlyViewedBookById should persist a single book for user")
    @Test
    void saveRecentlyViewedBookByIdPersistsEntity() {
        OpenLibraryByKeyResponseDTO dto = new OpenLibraryByKeyResponseDTO();
        dto.setId("key1");
        dto.setTitle("title1");
        String userId = "user2";
        service.saveRecentlyViewedBookById(dto, userId);
        verify(bookRecentlyViewRepository).save(any(BookRecentlyViewModel.class));
    }

    @DisplayName("saveRecentlyViewedBookByGender should persist all provided books for user")
    @Test
    void saveRecentlyViewedBookByGenderPersistsEntities() {
        BookByGenderDTO dto1 = new BookByGenderDTO();
        BookByGenderDTO dto2 = new BookByGenderDTO();
        String userId = "user3";
        service.saveRecentlyViewedBookByGender(List.of(dto1, dto2), userId);
        verify(bookRecentlyViewRepository).saveAll(anyList());
    }

    @DisplayName("getRecentlyViewedBooks returns books when found")
    @Test
    void getRecentlyViewedBooksReturnsBooks() {
        BookRecentlyViewModel book = new BookRecentlyViewModel();
        when(bookRecentlyViewRepository.findTop10ByUserIdOrderByViewedAtDesc("user4"))
                .thenReturn(List.of(book));
        List<BookRecentlyViewModel> result = service.getRecentlyViewedBooks("user4");
        assertThat(result).containsExactly(book);
    }

    @DisplayName("getRecentlyViewedBooks throws UserNotFoundException when no books found")
    @Test
    void getRecentlyViewedBooksThrowsExceptionWhenEmpty() {
        when(bookRecentlyViewRepository.findTop10ByUserIdOrderByViewedAtDesc("user5"))
                .thenReturn(Collections.emptyList());
        assertThatThrownBy(() -> service.getRecentlyViewedBooks("user5"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("user5");
    }
}