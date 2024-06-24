package com.OnbaordWeb.Services.Impl;

import com.OnbaordWeb.Models.Answer;
import com.OnbaordWeb.Repositories.AnswerRepository;
import com.OnbaordWeb.Services.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;

    //save
    @Override
    public Answer saveAnswer(Answer answer) {
        return null;
    }

    //getAnswer by quizId and QuesId
    @Override
    public Answer getAnswerByQuizIdAndQuestionId(Long quizId, Long questionId) {
        return null;
    }

    //findBy id
    @Override
    public Optional<Answer> findById(Long id) {
        return answerRepository.findById(id);
    }


}
