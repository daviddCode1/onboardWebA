package com.OnbaordWeb.Services;


import com.OnbaordWeb.Models.AttemptAnswer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AttemptAnswerService {

    AttemptAnswer saveAttemptAnswer(AttemptAnswer attemptAnswer);

    void saveAll(List<AttemptAnswer> attemptAnswers);

    List<AttemptAnswer> getAllAttemptAnswers();

    Optional<AttemptAnswer> findById(Long id);
}
