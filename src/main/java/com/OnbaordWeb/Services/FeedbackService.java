package com.OnbaordWeb.Services;

import com.OnbaordWeb.Models.Feedback;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface FeedbackService {

    Feedback save(Feedback feedback);

    List<Feedback> findAll();

    Optional<Feedback> findById(Long id);
}
