package com.OnbaordWeb.Services.Impl;

import com.OnbaordWeb.Models.Question;
import com.OnbaordWeb.Repositories.QuestionRepository;
import com.OnbaordWeb.Services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    //save
    @Override
    public Question saveQuestion(Question question) {
        return null;
    }

    //findAll
    @Override
    public List<Question> getAllQuestions() {
        return null;
    }

    //find all by quiz  id
    @Override
    public List<Question> getAllQuestionsByQuizId(Long quizId) {
        return null;
    }

    //find by id
    @Override
    public Optional<Question> findById(Long id) {
        return Optional.empty();
    }

    //get question by no
    public Question getQuestionByNo(Integer number){
        return questionRepository.getQuestionByNo(number);
    }
}
