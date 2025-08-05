package com.f1rst.bookapi.services.impl;

import com.f1rst.bookapi.clients.OpenLibraryClient;
import com.f1rst.bookapi.dtos.BookByGenderDTO;
import com.f1rst.bookapi.dtos.BookDTO;
import com.f1rst.bookapi.dtos.OpenLibraryByKeyResponseDTO;
import com.f1rst.bookapi.services.BookRecentlyViewService;
import com.f1rst.bookapi.services.BookService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    final OpenLibraryClient openLibraryClient;
    final RedisTemplate redisTemplate;
    final BookRecentlyViewService bookRecentlyViewService;

    public BookServiceImpl(OpenLibraryClient openLibraryClient, RedisTemplate redisTemplate, BookRecentlyViewService bookRecentlyViewService) {
        this.openLibraryClient = openLibraryClient;
        this.redisTemplate = redisTemplate;
        this.bookRecentlyViewService = bookRecentlyViewService;
    }


    @Override
    public List<BookDTO> getAllBooks(String q, int page, int limit, String userId) {
        String cacheKey = String.format("books:q=%s:page=%d:limit=%d", q, page, limit);

        List<BookDTO> booksFromCache = (List<BookDTO>) redisTemplate.opsForValue().get(cacheKey);
        if (booksFromCache != null) {
            return booksFromCache;
        }

        List<BookDTO> books = openLibraryClient.getAllBooks(q, page, limit);
        bookRecentlyViewService.saveRecentlyViewedBook(books, userId);

        redisTemplate.opsForValue().set(cacheKey, books, Duration.ofMinutes(5));
        return books;
    }

    @Override
    public List<BookDTO> getAllBooksByAuthor(String author, int page, int limit, String userId) {
        String cacheKey = String.format("getAllBooksByAuthor:q=%s:page=%d:limit=%d", author, page, limit);
        List<BookDTO> booksFromCache = (List<BookDTO>) redisTemplate.opsForValue().get(cacheKey);
        if (booksFromCache != null) {
            return booksFromCache;
        }

        List<BookDTO> books = openLibraryClient.getBooksByAuthor(author, page, limit);
        bookRecentlyViewService.saveRecentlyViewedBook(books, userId);

        redisTemplate.opsForValue().set(cacheKey, books, Duration.ofMinutes(5));
        return books;
    }

    @Override
    public OpenLibraryByKeyResponseDTO getBookById(String id, String userId) {
        String cacheKey = String.format("bookById=%s", id);
        OpenLibraryByKeyResponseDTO bookFromCache = (OpenLibraryByKeyResponseDTO) redisTemplate.opsForValue().get(cacheKey);
        if (bookFromCache != null) {
            return bookFromCache;
        }

        OpenLibraryByKeyResponseDTO book = openLibraryClient.getBookById(id);
//        bookRecentlyViewService.saveRecentlyViewedBook(book, userId);

        redisTemplate.opsForValue().set(cacheKey, book, Duration.ofMinutes(5));
        return book;
    }

    @Override
    public List<BookByGenderDTO> gelAllBooksByGender(String gender, int page, int limit, String userId) {
        String cacheKey = String.format("gelAllBooksByGender=%s:offset=%d:limit=%d", gender, page, limit);
        List<BookByGenderDTO> booksFromCache = (List<BookByGenderDTO>) redisTemplate.opsForValue().get(cacheKey);

        if (booksFromCache != null) {
            return booksFromCache;
        }

        List<BookByGenderDTO> books = openLibraryClient.getBooksByGender(gender, limit, page);
//        bookRecentlyViewService.saveRecentlyViewedBook(books, userId);

        redisTemplate.opsForValue().set(cacheKey, books, Duration.ofMinutes(5));
        return books;
    }
}
