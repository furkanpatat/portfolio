package com.furkanpatat.portfolio.repository;

import com.furkanpatat.portfolio.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    // Sadece yayında olan makaleleri ana sayfada göstermek için
    List<Article> findByIsPublishedTrueOrderByPublishedDateDesc();
}