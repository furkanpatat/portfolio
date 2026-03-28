package com.furkanpatat.portfolio.controller;

import com.furkanpatat.portfolio.entity.Article;
import com.furkanpatat.portfolio.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "*")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    // Tek bir makalenin detayını getiren endpoint
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        return articleRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Herkese açık endpoint: Sadece yayındaki makaleleri getirir
    @GetMapping("/published")
    public List<Article> getPublishedArticles() {
        return articleRepository.findByIsPublishedTrueOrderByPublishedDateDesc();
    }

    // Admin endpoint'i: Tüm makaleleri getirir (taslaklar dahil)
    @GetMapping
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    // Yeni makale ekleme (Admin)
    @PostMapping
    public Article createArticle(@RequestBody Article article) {
        if (article.getPublishedDate() == null) {
            article.setPublishedDate(LocalDate.now());
        }
        return articleRepository.save(article);
    }

    // Makale silme (Admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id) {
        articleRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}