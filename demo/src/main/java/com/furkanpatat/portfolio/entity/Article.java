package com.furkanpatat.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String content; // Uzun yazılar için TEXT tipi

    private String tags; // Örn: "Java, Spring Boot, Microservices"
    private LocalDate publishedDate;
    private String readTime; // Örn: "5 dk"
    private boolean isPublished;

    // Getter ve Setter metotlarını buraya eklemeyi unutma (veya Lombok @Data kullan)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
    public LocalDate getPublishedDate() { return publishedDate; }
    public void setPublishedDate(LocalDate publishedDate) { this.publishedDate = publishedDate; }
    public String getReadTime() { return readTime; }
    public void setReadTime(String readTime) { this.readTime = readTime; }
    @JsonProperty("isPublished")
    public boolean isPublished() { return isPublished; }
    public void setPublished(boolean isPublished) { this.isPublished = isPublished; }
}