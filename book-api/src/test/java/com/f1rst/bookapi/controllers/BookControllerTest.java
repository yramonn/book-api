package com.f1rst.bookapi.controllers;

import com.f1rst.bookapi.dtos.BookByGenderDTO;
import com.f1rst.bookapi.dtos.BookDTO;
import com.f1rst.bookapi.dtos.OpenLibraryByKeyResponseDTO;
import com.f1rst.bookapi.models.BookRecentlyViewModel;
import com.f1rst.bookapi.services.BookRecentlyViewService;
import com.f1rst.bookapi.services.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    BookService bookService;

    @Mock
    BookRecentlyViewService bookRecentlyViewService;

    @InjectMocks
    BookController controller;

    @DisplayName("getGeneralBooks Return list of books with valid parameters")
    @Test
    void getGeneralBooksReturnsListWithValidParameters() {
        String userId = "user1";
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId("book-123");
        bookDTO.setTitle("Clean Code");
        bookDTO.setAuthorNames(List.of("Robert C. Martin"));
        List<BookDTO> books = List.of(bookDTO);

        when(bookService.getAllBooks("java", 1, 10, userId)).thenReturn(books);

        ResponseEntity<List<BookDTO>> response = controller.getGeneralBooks("java", 1, 10, userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("getBookById return book by valid id")
    @Test
    void getBookByIdReturnsBookWithValidId() {
        String userId = "user2";
        OpenLibraryByKeyResponseDTO book = new OpenLibraryByKeyResponseDTO();
        book.setId("book-456");
        book.setTitle("Effective Java");
        when(bookService.getBookById("book-456", userId)).thenReturn(book);

        ResponseEntity<OpenLibraryByKeyResponseDTO> response = controller.getBookById("book-456", userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(book);
    }

    @DisplayName("getBooksByAuthor return a list of books with valid author")
    @Test
    void getBooksByAuthorReturnsListWithValidAuthor() {
        String userId = "user3";
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId("book-789");
        bookDTO.setTitle("Refactoring");
        bookDTO.setAuthorNames(List.of("Martin Fowler"));
        List<BookDTO> books = List.of(bookDTO);
        when(bookService.getAllBooksByAuthor("Martin Fowler", 1, 10, userId)).thenReturn(books);

        ResponseEntity<List<BookDTO>> response = controller.getBooksByAuthor("Martin Fowler", 1, 10, userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("getBooksByGender return a list of books with valid gender")
    @Test
    void getBooksByGenderReturnsListWithValidGender() {
        String userId = "user4";
        BookByGenderDTO dto = new BookByGenderDTO();
        dto.setId("book-321");
        dto.setTitle("Domain-Driven Design");
        List<BookByGenderDTO> books = List.of(dto);
        when(bookService.gelAllBooksByGender("Fiction", 1, 10, userId)).thenReturn(books);

        ResponseEntity<List<BookByGenderDTO>> response = controller.getBooksByGender("Fiction", 1, 10, userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("getRecentBooks return recently viewed books")
    @Test
    void getRecentBooksReturnsRecentlyViewedBooks() {
        String userId = "user5";
        BookRecentlyViewModel book = new BookRecentlyViewModel();
        book.setId(UUID.randomUUID());
        book.setTitle("Clean Code");
        List<BookRecentlyViewModel> books = List.of(book);
        when(bookRecentlyViewService.getRecentlyViewedBooks(userId)).thenReturn(books);

        ResponseEntity<List<BookRecentlyViewModel>> response = controller.getRecentBooks(userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
