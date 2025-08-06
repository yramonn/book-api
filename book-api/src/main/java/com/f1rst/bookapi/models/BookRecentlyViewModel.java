package com.f1rst.bookapi.models;

import com.f1rst.bookapi.dtos.BookByGenderDTO;
import com.f1rst.bookapi.dtos.BookDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_RECENTLY_VIEWED_BOOKS")
public class BookRecentlyViewModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String bookKey;

    @Column(nullable = false)
    private String title;

    private String authorNames;

    @Column(nullable = false)
    private LocalDateTime viewedAt;


    public static BookRecentlyViewModel buildEntity(BookDTO dto, String userId) {
        var bookRecentlyViewModel = new BookRecentlyViewModel();
        bookRecentlyViewModel.setUserId(userId);
        bookRecentlyViewModel.setBookKey(dto.getId());
        bookRecentlyViewModel.setTitle(dto.getTitle());
        bookRecentlyViewModel.setAuthorNames(dto.getAuthorNames().getFirst());
        bookRecentlyViewModel.setViewedAt(LocalDateTime.now());

        return bookRecentlyViewModel;
    }

    public static BookRecentlyViewModel buildEntity(BookByGenderDTO dto, String userId) {
        var bookRecentlyViewModel = new BookRecentlyViewModel();
        bookRecentlyViewModel.setUserId(userId);
        bookRecentlyViewModel.setBookKey(dto.getId());
        bookRecentlyViewModel.setTitle(dto.getTitle());
        bookRecentlyViewModel.setViewedAt(LocalDateTime.now());

        return bookRecentlyViewModel;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookKey() {
        return bookKey;
    }

    public void setBookKey(String bookKey) {
        this.bookKey = bookKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorNames() {
        return authorNames;
    }

    public void setAuthorNames(String authorNames) {
        this.authorNames = authorNames;
    }

    public LocalDateTime getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(LocalDateTime viewedAt) {
        this.viewedAt = viewedAt;
    }
}
