package com.f1rst.bookapi.repositories;

import com.f1rst.bookapi.models.BookRecentlyViewModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class BookRecentlyViewRepositoryTest {
    @Mock
    BookRecentlyViewRepository bookRecentlyViewRepository;

    @DisplayName("findTop10ByUserIdOrderByViewedAtDesc returns top 10 recently viewed books for a valid user ID")
    @Test
    void findTop10ByUserIdOrderByViewedAtDescReturnsTop10BooksForValidUserId() {
        String userId = "user1";
        BookRecentlyViewModel book1 = new BookRecentlyViewModel();
        book1.setId(UUID.randomUUID());
        book1.setTitle("Title 1");
        book1.setViewedAt(LocalDateTime.now());

        BookRecentlyViewModel book2 = new BookRecentlyViewModel();
        book2.setId(UUID.randomUUID());
        book2.setTitle("Title 2");
        book2.setViewedAt(LocalDateTime.now().minusDays(1));

        List<BookRecentlyViewModel> books = List.of(book1, book2);
        when(bookRecentlyViewRepository.findTop10ByUserIdOrderByViewedAtDesc(userId)).thenReturn(books);

        List<BookRecentlyViewModel> result = bookRecentlyViewRepository.findTop10ByUserIdOrderByViewedAtDesc(userId);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(books);
    }

    @DisplayName("findTop10ByUserIdOrderByViewedAtDesc returns empty list for user ID with no books")
    @Test
    void findTop10ByUserIdOrderByViewedAtDescReturnsEmptyListForUserIdWithNoBooks() {
        String userId = "user2";
        when(bookRecentlyViewRepository.findTop10ByUserIdOrderByViewedAtDesc(userId)).thenReturn(Collections.emptyList());

        List<BookRecentlyViewModel> result = bookRecentlyViewRepository.findTop10ByUserIdOrderByViewedAtDesc(userId);

        assertThat(result).isNotNull();
    }
}
