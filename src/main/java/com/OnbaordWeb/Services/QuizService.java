package com.OnbaordWeb.Services;

import com.OnbaordWeb.Models.Quiz;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface QuizService {

    Quiz saveQuiz(Quiz quiz);

    List<Quiz> getAllQuizes();

    Quiz getQuizByNo(Integer quizNo);

    Optional<Quiz> findById(Long id);
}
