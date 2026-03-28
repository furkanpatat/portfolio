package com.furkanpatat.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "experiences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName; // Örn: "KCR YAZILIM" VEYA "FLO GROUP"

    @Column(nullable = false)
    private String title; // Örn: "JR. BACKEND GELİŞTİRİCİ" veya "YAZILIM STAJYERİ"

    private String startDate; // Örn: "KASIM 2025"
    private String endDate;   // Örn: "DEVAM EDİYOR..." veya "29/08/2025"

    @Column(columnDefinition = "TEXT")
    private String description; // Yaptığın işin detayı

    // Projelerdeki gibi sıralama ve gizleme özellikleri burada da çok işimize yarayacak
    @Builder.Default
    private Integer displayOrder = 0;

    @Builder.Default
    private boolean isActive = true;
}