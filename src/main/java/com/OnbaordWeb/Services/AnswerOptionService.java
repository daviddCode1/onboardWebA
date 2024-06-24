package com.OnbaordWeb.Services;


import com.OnbaordWeb.Models.AnswerOption;
import com.OnbaordWeb.Models.Question;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AnswerOptionService {

    AnswerOption saveAnswerOption(AnswerOption answerOption);

    List<AnswerOption> getAllAnswerOptions();

    List<AnswerOption> getAllAnswerOptionsByQuizIdAndQuestionId(Long quizId, Long questionId);

    Optional<AnswerOption> findById(Long id);

    AnswerOption getAnswerOptionByOptionNoAndQusNo(String optionNo, Question questionNo);
}
