package com.OnbaordWeb.Services;


import com.OnbaordWeb.Models.Answer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AnswerService {
    Answer saveAnswer(Answer answer);

    Answer getAnswerByQuizIdAndQuestionId(Long quizId, Long questionId);

    Optional<Answer> findById(Long id);


}
