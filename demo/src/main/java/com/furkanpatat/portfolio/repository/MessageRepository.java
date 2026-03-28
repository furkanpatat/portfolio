package com.furkanpatat.portfolio.repository;

import com.furkanpatat.portfolio.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // Admin paneline en yeni mesajlar en üstte düşsün diye tarihe göre ters sıralıyoruz
    List<Message> findAllByOrderBySentAtDesc();

    // Panelde "Okunmamış X mesajınız var" demek için
    List<Message> findAllByIsReadFalseOrderBySentAtDesc();
}