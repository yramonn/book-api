package com.f1rst.bookapi.repositories;

import com.f1rst.bookapi.models.BookRecentlyViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface BookRecentlyViewRepository extends JpaRepository<BookRecentlyViewModel, UUID> {

    List<BookRecentlyViewModel> findTop10ByUserIdOrderByViewedAtDesc(String userId);
}
