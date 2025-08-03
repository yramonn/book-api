package com.f1rst.bookapi.services.impl;

import com.f1rst.bookapi.clients.OpenLibraryClient;
import com.f1rst.bookapi.dtos.BookDTO;
import com.f1rst.bookapi.services.BookService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    final OpenLibraryClient openLibraryClient;
    final RedisTemplate redisTemplate;

    public BookServiceImpl(OpenLibraryClient openLibraryClient, RedisTemplate redisTemplate) {
        this.openLibraryClient = openLibraryClient;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public List<BookDTO> getAllBooks(String q, int page, int limit) {
        String cacheKey = String.format("books:q=%s:page=%d:limit=%d", q, page, limit);

        List<BookDTO> booksFromCache = (List<BookDTO>) redisTemplate.opsForValue().get(cacheKey);
        if(booksFromCache != null) {
           return booksFromCache;
        }

        List<BookDTO> books = openLibraryClient.getAllBooks(q, page, limit);
        redisTemplate.opsForValue().set(cacheKey, books, Duration.ofMinutes(5));
        return books;
    }
}
