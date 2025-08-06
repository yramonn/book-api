package com.f1rst.bookapi.services.impl;

import com.f1rst.bookapi.clients.OpenLibraryClient;
import com.f1rst.bookapi.dtos.BookByGenderDTO;
import com.f1rst.bookapi.dtos.BookDTO;
import com.f1rst.bookapi.dtos.OpenLibraryByKeyResponseDTO;
import com.f1rst.bookapi.services.BookRecentlyViewService;
import com.f1rst.bookapi.services.BookService;
import com.f1rst.bookapi.utils.RedisUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    final OpenLibraryClient openLibraryClient;
    final RedisUtils redisUtils;
    final BookRecentlyViewService bookRecentlyViewService;

    public BookServiceImpl(OpenLibraryClient openLibraryClient, RedisUtils redisUtils, BookRecentlyViewService bookRecentlyViewService) {
        this.openLibraryClient = openLibraryClient;
        this.redisUtils = redisUtils;
        this.bookRecentlyViewService = bookRecentlyViewService;
    }


    @Override
    public List<BookDTO> getAllBooks(String q, int page, int limit, String userId) {
        String cacheKey = String.format("getAllBooks:q=%s:page=%d:limit=%d", q, page, limit);

        List<BookDTO> responseFromCache = redisUtils.getFromCache(cacheKey);
        if (responseFromCache != null) {
            return responseFromCache;
        }

        List<BookDTO> response = openLibraryClient.getAllBooks(q, page, limit);
        bookRecentlyViewService.saveRecentlyViewedBook(response, userId);

        redisUtils.saveToCache(cacheKey, response);
        return response;
    }

    @Override
    public List<BookDTO> getAllBooksByAuthor(String author, int page, int limit, String userId) {
        String cacheKey = String.format("getAllBooksByAuthor:q=%s:page=%d:limit=%d", author, page, limit);

        List<BookDTO> responseFromCache = redisUtils.getFromCache(cacheKey);
        if (responseFromCache != null) {
            return responseFromCache;
        }

        List<BookDTO> response = openLibraryClient.getBooksByAuthor(author, page, limit);
        bookRecentlyViewService.saveRecentlyViewedBook(response, userId);

        redisUtils.saveToCache(cacheKey, response);
        return response;
    }

    @Override
    public OpenLibraryByKeyResponseDTO getBookById(String id, String userId) {
        String cacheKey = String.format("getBookById=%s", id);

        OpenLibraryByKeyResponseDTO responseFromCache = redisUtils.getFromCache(cacheKey);
        if (responseFromCache != null) {
            return responseFromCache;
        }

        OpenLibraryByKeyResponseDTO response = openLibraryClient.getBookById(id);

        bookRecentlyViewService.saveRecentlyViewedBookById(response, userId);

        redisUtils.saveToCache(cacheKey, response);
        return response;
    }

    @Override
    public List<BookByGenderDTO> gelAllBooksByGender(String gender, int page, int limit, String userId) {
        String cacheKey = String.format("gelAllBooksByGender=%s:offset=%d:limit=%d", gender, page, limit);

        List<BookByGenderDTO> responseFromCache = redisUtils.getFromCache(cacheKey);

        if (responseFromCache != null) {
            return responseFromCache;
        }

        List<BookByGenderDTO> response = openLibraryClient.getBooksByGender(gender, limit, page);
        bookRecentlyViewService.saveRecentlyViewedBookByGender(response, userId);

        redisUtils.saveToCache(cacheKey, response);
        return response;
    }
}
