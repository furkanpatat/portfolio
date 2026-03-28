package com.furkanpatat.portfolio.repository;

import com.furkanpatat.portfolio.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    // Aynı IP'den gelenleri tekrar saymamak için bir metot
    boolean existsByIpAddress(String ipAddress);
}