package com.OnbaordWeb.Services.Impl;

import com.OnbaordWeb.Dtos.AttemptAnswerDto;
import com.OnbaordWeb.Models.*;
import com.OnbaordWeb.Repositories.AttemptAnswerRepository;
import com.OnbaordWeb.Services.AttemptAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttemptAnswerServiceImpl implements AttemptAnswerService {

    private final AttemptAnswerRepository attemptAnswerRepository;
    private final QuestionServiceImpl questionServiceImpl;
    private final QuizServiceImpl quizServiceImpl;
    private final AnswerOptionServiceImpl answerOptionServiceImpl;

    //save
    @Override
    public AttemptAnswer saveAttemptAnswer(AttemptAnswer attemptAnswer) {
        return attemptAnswerRepository.save(attemptAnswer);
    }

    //save all
    @Override
    public void saveAll(List<AttemptAnswer> attemptAnswers) {
         attemptAnswerRepository.saveAll(attemptAnswers);
    }

    //find all
    @Override
    public List<AttemptAnswer> getAllAttemptAnswers() {
        return attemptAnswerRepository.findAll();
    }

    //find by id
    @Override
    public Optional<AttemptAnswer> findById(Long id) {
        return attemptAnswerRepository.findById(id);
    }

    //save attempt answer by attemptAnswer Dto list
    public boolean saveAttemptAnswerByDtoList(List<AttemptAnswerDto> attemptAnswerDtos, Attempt attempt){
        boolean isSave = false;

//check attempt and dto
        if(attempt != null && !attemptAnswerDtos.isEmpty()){
            List<AttemptAnswer> attemptAnswerList = new ArrayList<>();
            Long attId = attempt.getAttemptId();

            //loop for list
            for(AttemptAnswerDto attemptAnswerDto : attemptAnswerDtos){

                //check
                if(attemptAnswerDto != null) {

                    //get question
                    Question question = questionServiceImpl.getQuestionByNo(attemptAnswerDto.getQuestionId());

                    //get quiz
                    Quiz quiz = quizServiceImpl.getAllQuizes().get(0);

                    //get answer option
                    AnswerOption answerOption = answerOptionServiceImpl.getAnswerOptionByOptionNoAndQusNo(attemptAnswerDto.getAnswerNo(), question);

                    //check
                    if(question != null && quiz != null && answerOption != null) {

                        AttemptAnswer attemptAnswer = new AttemptAnswer();

                        //add values
                        attemptAnswer.setAnswerOptionId(answerOption.getOptionId());
                        attemptAnswer.setQuizId(quiz.getQuizId());
                        attemptAnswer.setQuestionId(question.getQuestionId());
                        attemptAnswer.setAttemptId(attId);

                        //add to list
                        attemptAnswerList.add(attemptAnswer);


                    }
                }
            }

                try {
                    saveAll(attemptAnswerList);
                    isSave = true;
                }catch (Exception e){
                    isSave = false;
                }

        }

        return isSave;
    }
}
