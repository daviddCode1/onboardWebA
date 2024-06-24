package com.OnbaordWeb.Services;

import com.OnbaordWeb.Models.Question;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface QuestionService {

    Question saveQuestion(Question question);

    List<Question> getAllQuestions();

    List<Question> getAllQuestionsByQuizId(Long quizId);

    Optional<Question> findById(Long id);
}
