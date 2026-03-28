package com.furkanpatat.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonProperty; // 1. BU İMPORTU EKLE
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 2. FRONTEND'DEN GELEN "name" JSON'UNU BURAYA EŞLE
    @JsonProperty("name")
    @Column(nullable = false)
    private String senderName;

    // 3. FRONTEND'DEN GELEN "email" JSON'UNU BURAYA EŞLE
    @JsonProperty("email")
    @Column(nullable = false)
    private String senderEmail;

    private String subject;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private LocalDateTime sentAt;

    private Boolean isRead;

    @PrePersist
    public void prePersist() {
        if (this.sentAt == null) {
            this.sentAt = LocalDateTime.now();
        }
        if (this.isRead == null) {
            this.isRead = false;
        }
    }
}