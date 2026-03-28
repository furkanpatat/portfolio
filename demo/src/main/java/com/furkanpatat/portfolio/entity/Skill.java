package com.furkanpatat.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Örn: "Spring Boot", "Docker", "LightGBM"

    @Column(nullable = false)
    private String category; // Örn: "Backend", "Cloud & DevOps", "Data & ML"

    // Yine sıralama ve aktiflik durumunu unutmuyoruz
    @Builder.Default
    private Integer displayOrder = 0;

    @Builder.Default
    private boolean isActive = true;
}