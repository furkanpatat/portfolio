package com.furkanpatat.portfolio.service;

import com.furkanpatat.portfolio.entity.Message;
import com.furkanpatat.portfolio.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    // Site üzerinden yeni mesaj gönderildiğinde
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    // Admin panelinde tüm mesajları listelemek için
    public List<Message> getAllMessages() {
        return messageRepository.findAllByOrderBySentAtDesc();
    }

    // Sadece okunmamışları getirmek için
    public List<Message> getUnreadMessages() {
        return messageRepository.findAllByIsReadFalseOrderBySentAtDesc();
    }

    public Message getMessageById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesaj bulunamadı! ID: " + id));
    }

    // Mesajı "Okundu" olarak işaretlemek için
    public Message markAsRead(Long id) {
        Message message = getMessageById(id);
        message.setIsRead(true);
        return messageRepository.save(message);
    }

    // Spam veya gereksiz mesajları silmek için
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }
}