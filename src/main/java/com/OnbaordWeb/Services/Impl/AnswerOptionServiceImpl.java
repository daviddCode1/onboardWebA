package com.OnbaordWeb.Services.Impl;

import com.OnbaordWeb.Models.AnswerOption;
import com.OnbaordWeb.Models.Question;
import com.OnbaordWeb.Repositories.AnswerOptionRepository;
import com.OnbaordWeb.Services.AnswerOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerOptionServiceImpl implements AnswerOptionService {

    private final AnswerOptionRepository answerOptionRepository;

    //save
    @Override
    public AnswerOption saveAnswerOption(AnswerOption answerOption) {
        return null;
    }

    //find all
    @Override
    public List<AnswerOption> getAllAnswerOptions() {
        return null;
    }

    //get answer by Id
    @Override
    public List<AnswerOption> getAllAnswerOptionsByQuizIdAndQuestionId(Long quizId, Long questionId) {
        return null;
    }

    //get answer by Id
    @Override
    public Optional<AnswerOption> findById(Long id) {
        return Optional.empty();
    }

    //get answerOption by optionNo and Question No
    @Override
    public AnswerOption getAnswerOptionByOptionNoAndQusNo(String optionNo, Question questionNo) {
        return answerOptionRepository.getAnswerOptionByOptionNoAndQues(optionNo, questionNo);

    }
}
