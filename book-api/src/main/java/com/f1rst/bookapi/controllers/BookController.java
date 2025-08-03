package com.f1rst.bookapi.controllers;

import com.f1rst.bookapi.dtos.BookDTO;
import com.f1rst.bookapi.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getGeneralBooks( @RequestParam String q,
                                                      @RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.
                status(HttpStatus.OK).
                body(bookService.getAllBooks(q, page, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<BookDTO>> getBookById(@PathVariable(value = "id") String id) {
            return ResponseEntity.
                    status(HttpStatus.OK).
                    body(bookService.getBookById(id));

    }

    @GetMapping("/author")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@RequestParam String author,
                                                          @RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.
                status(HttpStatus.OK).
                body(bookService.getAllBooksByAuthor(author, page, limit));
    }

    @GetMapping("/gender")
    public ResponseEntity<List<BookDTO>> getBooksByGender(@PathVariable(value = "gender") String gender,
                                                          @RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.
                status(HttpStatus.OK).
                body(bookService.gelAllBooksByGender(gender, page, limit));
    }
}

