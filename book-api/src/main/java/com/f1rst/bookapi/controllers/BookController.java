package com.f1rst.bookapi.controllers;

import com.f1rst.bookapi.dtos.BookDTO;
import com.f1rst.bookapi.models.BookRecentlyViewModel;
import com.f1rst.bookapi.services.BookRecentlyViewService;
import com.f1rst.bookapi.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    final BookService bookService;
    final BookRecentlyViewService bookRecentlyViewService;

    public BookController(BookService bookService, BookRecentlyViewService bookRecentlyViewService) {
        this.bookService = bookService;
        this.bookRecentlyViewService = bookRecentlyViewService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getGeneralBooks(@RequestParam String q,
                                                         @RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int limit,
                                                         @RequestHeader("userId") String userId) {
        return ResponseEntity.
                status(HttpStatus.OK).
                body(bookService.getAllBooks(q, page, limit, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<BookDTO>> getBookById(@PathVariable(value = "id") String id,
                                                     @RequestHeader("userId") String userId) {
            return ResponseEntity.
                    status(HttpStatus.OK).
                    body(bookService.getBookById(id, userId));

    }

    @GetMapping("/author")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@RequestParam String author,
                                                          @RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "10") int limit,
                                                          @RequestHeader("userId") String userId) {
        return ResponseEntity.
                status(HttpStatus.OK).
                body(bookService.getAllBooksByAuthor(author, page, limit, userId));
    }

    @GetMapping("/gender")
    public ResponseEntity<List<BookDTO>> getBooksByGender(@PathVariable(value = "gender") String gender,
                                                          @RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "10") int limit,
                                                          @RequestHeader("userId") String userId) {
        return ResponseEntity.
                status(HttpStatus.OK).
                body(bookService.gelAllBooksByGender(gender, page, limit, userId));
    }

    @GetMapping("/users/{userId}/recently-viewed")
    public ResponseEntity<List<BookRecentlyViewModel>> getRecentBooks(@PathVariable String userId) {
        return ResponseEntity.ok(bookRecentlyViewService.getRecentlyViewedBooks(userId));
    }
}

