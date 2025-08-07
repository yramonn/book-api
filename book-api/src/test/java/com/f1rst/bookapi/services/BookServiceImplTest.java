package com.f1rst.bookapi.services;

import com.f1rst.bookapi.clients.OpenLibraryClient;
import com.f1rst.bookapi.dtos.BookByGenderDTO;
import com.f1rst.bookapi.dtos.BookDTO;
import com.f1rst.bookapi.dtos.OpenLibraryByKeyResponseDTO;
import com.f1rst.bookapi.services.impl.BookServiceImpl;
import com.f1rst.bookapi.utils.RedisUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    @Mock
    RedisUtils redisUtils;

    @Mock
    OpenLibraryClient openLibraryClient;

    @Mock
    BookRecentlyViewService bookRecentlyViewService;

    @InjectMocks
    BookServiceImpl service;

    @DisplayName("getAllBooks return response from redis")
    @Test
    void getAllBooksReturnsFromCacheIfPresent() {
        String cacheKey = "getAllBooks:q=java:page=1:limit=10";
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId("book-123");
        bookDTO.setTitle("Java Effective");
        bookDTO.setAuthorNames(List.of("Joshua Bloch"));
        when(redisUtils.getFromCache(cacheKey)).thenReturn(List.of(bookDTO));
        List<BookDTO> result = service.getAllBooks("java", 1, 10, "user1");
        assertThat(result).containsExactly(bookDTO);
        verify(openLibraryClient, never()).getAllBooks(anyString(), anyInt(), anyInt());
    }

    @DisplayName("getAllBooks Search books from API and save to cache when no cache")
    @Test
    void getAllBooksFetchesAndCachesWhenNoCache() {
        String cacheKey = "getAllBooks:q=spring:page=2:limit=5";
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId("book-456");
        bookDTO.setTitle("Spring in Action");
        bookDTO.setAuthorNames(List.of("Craig Walls"));
        when(redisUtils.getFromCache(cacheKey)).thenReturn(null);
        when(openLibraryClient.getAllBooks("spring", 2, 5)).thenReturn(List.of(bookDTO));
        List<BookDTO> result = service.getAllBooks("spring", 2, 5, "user2");
        assertThat(result).containsExactly(bookDTO);
        verify(bookRecentlyViewService).saveRecentlyViewedBook(List.of(bookDTO), "user2");
        verify(redisUtils).saveToCache(cacheKey, List.of(bookDTO));
    }

    @DisplayName("getAllBooksByAuthor return from cache")
    @Test
    void getAllBooksByAuthorReturnsFromCacheIfPresent() {
        String cacheKey = "getAllBooksByAuthor:q=Martin Fowler:page=1:limit=3";
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId("book-789");
        bookDTO.setTitle("Refactoring");
        bookDTO.setAuthorNames(List.of("Martin Fowler"));
        when(redisUtils.getFromCache(cacheKey)).thenReturn(List.of(bookDTO));
        List<BookDTO> result = service.getAllBooksByAuthor("Martin Fowler", 1, 3, "user3");
        assertThat(result).containsExactly(bookDTO);
        verify(openLibraryClient, never()).getBooksByAuthor(anyString(), anyInt(), anyInt());
    }

    @DisplayName("getBookById return from cache")
    @Test
    void getBookByIdReturnsFromCacheIfPresent() {
        String cacheKey = "getBookById=book-321";
        OpenLibraryByKeyResponseDTO dto = new OpenLibraryByKeyResponseDTO();
        dto.setId("book-321");
        dto.setTitle("Domain-Driven Design");
        when(redisUtils.getFromCache(cacheKey)).thenReturn(dto);
        OpenLibraryByKeyResponseDTO result = service.getBookById("book-321", "user4");
        assertThat(result).isEqualTo(dto);
        verify(openLibraryClient, never()).getBookById(anyString());
    }

    @DisplayName("gelAllBooksByGender return from cache")
    @Test
    void gelAllBooksByGenderReturnsFromCacheIfPresent() {
        String cacheKey = "gelAllBooksByGender=Fiction:offset=1:limit=2";
        BookByGenderDTO dto = new BookByGenderDTO();
        when(redisUtils.getFromCache(cacheKey)).thenReturn(List.of(dto));
        List<BookByGenderDTO> result = service.gelAllBooksByGender("Fiction", 1, 2, "user5");
        assertThat(result).containsExactly(dto);
        verify(openLibraryClient, never()).getBooksByGender(anyString(), anyInt(), anyInt());
    }

    @DisplayName("gelAllBooksByGender Search frpom API and save to cache when no cache")
    @Test
    void gelAllBooksByGenderFetchesAndCachesWhenNoCache() {
        String cacheKey = "gelAllBooksByGender=Non-Fiction:offset=2:limit=4";
        BookByGenderDTO dto = new BookByGenderDTO();
        when(redisUtils.getFromCache(cacheKey)).thenReturn(null);
        when(openLibraryClient.getBooksByGender("Non-Fiction", 4, 2)).thenReturn(List.of(dto));
        List<BookByGenderDTO> result = service.gelAllBooksByGender("Non-Fiction", 2, 4, "user6");
        assertThat(result).containsExactly(dto);
        verify(bookRecentlyViewService).saveRecentlyViewedBookByGender(List.of(dto), "user6");
        verify(redisUtils).saveToCache(cacheKey, List.of(dto));
    }
}
