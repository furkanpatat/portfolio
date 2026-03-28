package com.furkanpatat.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admin_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; // Örn: "furkanadmin"

    @Column(nullable = false)
    private String password; // Veritabanında asla düz metin (123456) olarak durmayacak, BCrypt ile şifreleyeceğiz.
}