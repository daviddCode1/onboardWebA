package com.OnbaordWeb.Services.Impl;

import com.OnbaordWeb.Models.Document;
import com.OnbaordWeb.Repositories.DocumentRepository;
import com.OnbaordWeb.Services.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    //findAll
    @Override
    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    //find by id
    @Override
    public Optional<Document> findById(Long id) {
        return documentRepository.findById(id);
    }
}
