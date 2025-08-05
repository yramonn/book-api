package com.f1rst.bookapi.repositories;

import com.f1rst.bookapi.models.BookRecentlyViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface BookRecentlyViewRepository extends JpaRepository<BookRecentlyViewModel, UUID> {

    @Query("SELECT r FROM BookRecentlyViewModel r WHERE r.userId = :userId ORDER BY r.viewedAt DESC")
    List<BookRecentlyViewModel> findTop10ByUserIdOrderByViewedAtDesc(String userId);
}
