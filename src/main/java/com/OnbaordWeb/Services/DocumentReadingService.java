package com.OnbaordWeb.Services;

import com.OnbaordWeb.Models.DocumentReading;
import com.OnbaordWeb.Models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DocumentReadingService {

    List<DocumentReading> saveAll(List<DocumentReading> documentReadings);
    DocumentReading save(DocumentReading documentReading);

    List<DocumentReading> findAll();

    Optional<DocumentReading> findById(Long id);

    List<DocumentReading> findByUser(User user);

    void deleteByUser(User user);
}
