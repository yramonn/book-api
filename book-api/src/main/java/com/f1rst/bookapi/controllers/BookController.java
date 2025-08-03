package com.f1rst.bookapi.controllers;

import com.f1rst.bookapi.dtos.BookDTO;
import com.f1rst.bookapi.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}

