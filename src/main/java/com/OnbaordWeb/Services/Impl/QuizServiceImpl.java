package com.OnbaordWeb.Services.Impl;

import com.OnbaordWeb.Dtos.QuizDto;
import com.OnbaordWeb.Exceptions.ResourceNotFoundException;
import com.OnbaordWeb.Models.Quiz;
import com.OnbaordWeb.Repositories.QuizRepository;
import com.OnbaordWeb.Services.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    //save
    @Override
    public Quiz saveQuiz(Quiz quiz) {
        return null;
    }

    //find all
    @Override
    public List<Quiz> getAllQuizes() {
        return quizRepository.findAll();
    }

    //get quiz by no
    @Override
    public Quiz getQuizByNo(Integer quizNo) {

        //check quizNo
        if(quizNo > 0){

            //get from DB
            Quiz quiz = quizRepository.getQuizByQuizNo(quizNo);

            //check
            if(quiz != null){
                return quiz;
            }
        }


        return null;
    }

    //find by id
    @Override
    public Optional<Quiz> findById(Long id) {
        return quizRepository.findById(id);
    }

    //get quiz dto by id
    public QuizDto getQuizDtoById(Long id){

        Quiz quiz = findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Quiz not found!"));

        if(quiz != null){
            QuizDto quizDto = new QuizDto();

            //add values
            quizDto.setQuizId(quiz.getQuizId());
            quizDto.setQuizNo(quiz.getQuizNo());
            quizDto.setName(quiz.getName());
            quizDto.setQuestions(quiz.getQuestions());

            return quizDto;
        }

        return null;
    }
}
