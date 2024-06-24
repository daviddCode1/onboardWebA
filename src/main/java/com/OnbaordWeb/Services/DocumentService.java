package com.OnbaordWeb.Services;


import com.OnbaordWeb.Models.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DocumentService {

    List<Document> findAll();

    Optional<Document> findById(Long id);
}
