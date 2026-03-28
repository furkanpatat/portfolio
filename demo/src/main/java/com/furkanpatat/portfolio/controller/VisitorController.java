package com.furkanpatat.portfolio.controller;

import com.furkanpatat.portfolio.entity.Visitor;
import com.furkanpatat.portfolio.repository.VisitorRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/visitors")
@CrossOrigin(origins = "*") // Frontend'den gelen isteklere izin veriyoruz
public class VisitorController {

    @Autowired
    private VisitorRepository visitorRepository;

    @PostMapping("/track")
    public ResponseEntity<Long> trackVisit(HttpServletRequest request) {
        // Ziyaretçinin IP adresini alıyoruz
        String ip = request.getRemoteAddr();

        // Eğer bu IP daha önce kaydedilmemişse, yeni ziyaretçi olarak ekle
        if (!visitorRepository.existsByIpAddress(ip)) {
            Visitor newVisitor = new Visitor();
            newVisitor.setIpAddress(ip);
            newVisitor.setVisitTime(LocalDateTime.now());
            visitorRepository.save(newVisitor);
        }

        // Toplam benzersiz ziyaretçi sayısını döndür
        return ResponseEntity.ok(visitorRepository.count());
    }
}