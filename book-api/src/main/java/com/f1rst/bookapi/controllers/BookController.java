package com.f1rst.bookapi.controllers;

import com.f1rst.bookapi.dtos.BookByGenderDTO;
import com.f1rst.bookapi.dtos.BookDTO;
import com.f1rst.bookapi.dtos.OpenLibraryByKeyResponseDTO;
import com.f1rst.bookapi.models.BookRecentlyViewModel;
import com.f1rst.bookapi.services.BookRecentlyViewService;
import com.f1rst.bookapi.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Tag(name = "Book", description = "Seaches books from OpenLibrary and manages recently viewed books by users")
public class BookController {

    final BookService bookService;
    final BookRecentlyViewService bookRecentlyViewService;

    public BookController(BookService bookService, BookRecentlyViewService bookRecentlyViewService) {
        this.bookService = bookService;
        this.bookRecentlyViewService = bookRecentlyViewService;
    }

    @GetMapping
    @Operation(
            summary = "Search general books.",
            description = "Return a list of books from OpenLibrary."
    )
    public ResponseEntity<List<BookDTO>> getGeneralBooks(@RequestParam String q,
                                                         @RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int limit,
                                                         @RequestHeader("userId") String userId) {
        return ResponseEntity.
                status(HttpStatus.OK).
                body(bookService.getAllBooks(q, page, limit, userId));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Find book by Id.",
            description = "Return book by Book key from OpenLibrary."
    )
    public ResponseEntity<OpenLibraryByKeyResponseDTO> getBookById(@PathVariable(value = "id") String id,
                                                                   @RequestHeader("userId") String userId) {
            return ResponseEntity.
                    status(HttpStatus.OK).
                    body(bookService.getBookById(id, userId));

    }

    @Operation(
            summary = "Find book by author.",
            description = "Return book by Author from OpenLibrary."
    )
    @GetMapping("/author")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@RequestParam String author,
                                                          @RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "10") int limit,
                                                          @RequestHeader("userId") String userId) {
        return ResponseEntity.
                status(HttpStatus.OK).
                body(bookService.getAllBooksByAuthor(author, page, limit, userId));
    }

    @Operation(
            summary = "Find book by gender.",
            description = "Return book by Gender from OpenLibrary."
    )
    @GetMapping("/gender")
    public ResponseEntity<List<BookByGenderDTO>> getBooksByGender(@RequestParam(value = "gender") String gender,
                                                                  @RequestParam(defaultValue = "1") int page,
                                                                  @RequestParam(defaultValue = "10") int limit,
                                                                  @RequestHeader("userId") String userId) {
        return ResponseEntity.
                status(HttpStatus.OK).
                body(bookService.gelAllBooksByGender(gender, page, limit, userId));
    }


    @Operation(
            summary = "Get recently viewed books by user.",
            description = "Return a list of recently viewed books by user."
    )
    @GetMapping("/users/{userId}/recently-viewed")
    public ResponseEntity<List<BookRecentlyViewModel>> getRecentBooks(@PathVariable String userId) {
        return ResponseEntity.ok(bookRecentlyViewService.getRecentlyViewedBooks(userId));
    }
}

