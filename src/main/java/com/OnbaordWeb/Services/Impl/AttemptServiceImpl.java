package com.OnbaordWeb.Services.Impl;

import com.OnbaordWeb.Dtos.AttemptAnswerDto;
import com.OnbaordWeb.Dtos.AttemptDto;
import com.OnbaordWeb.Exceptions.UserNotFoundException;
import com.OnbaordWeb.Models.Attempt;
import com.OnbaordWeb.Models.Quiz;
import com.OnbaordWeb.Models.User;
import com.OnbaordWeb.Repositories.AttemptRepository;
import com.OnbaordWeb.Services.AttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AttemptServiceImpl implements AttemptService {

    private final AttemptRepository attemptRepository;
    private final UserServiceImpl userServiceImpl;
    private final QuizServiceImpl quizServiceImpl;
    private final AttemptAnswerServiceImpl attemptAnswerServiceImpl;
    private final DateService dateService;

    //save
    @Override
    public Attempt saveAttempt(Attempt attempt) {
        return attemptRepository.save(attempt);
    }

    //finAll
    @Override
    public List<Attempt> getAllAttempts() {
        return attemptRepository.findAll();
    }

    //find by id
    @Override
    public Optional<Attempt> findById(Long id) {
        return attemptRepository.findById(id);
    }

    //get attempts by user id
    public List<AttemptDto> getAllAttemptsByUserId(Long userId){

        if(userId > 0){
            //get user and quiz
            User user = userServiceImpl.findById(userId).orElseThrow(() -> new UserNotFoundException("User not Found!"));
            Quiz quiz = quizServiceImpl.getAllQuizes().get(0);

            //check
            if(user != null && quiz != null)
            {
                //get attempt list by user id
                List<Attempt> attemptList = attemptRepository.getAllAttemptsByUserId(user, quiz);

                if(!attemptList.isEmpty()){

                    List<AttemptDto> attemptDtoList = new ArrayList<>();

                    for(Attempt attempt : attemptList) {
                        AttemptDto attemptDto = new AttemptDto();

                        //add values
                        attemptDto.setAttemptId(attempt.getAttemptId());
                        attemptDto.setQuizId(attempt.getQuiz().getQuizId());
                        attemptDto.setUserId(attempt.getUser().getUserId());
                        attemptDto.setCorrectCount(attempt.getCorrectCount());
                        attemptDto.setScore(attempt.getScore());

                        //add to list
                            attemptDtoList.add(attemptDto);

                    }

                    return attemptDtoList;
                }
            }

        }

        return  null;
    }

    //get all attempts as dto
    public List<AttemptDto> getAllAttemptsListAsDto(){

        //get quiz
            Quiz quiz = quizServiceImpl.getAllQuizes().get(0);

            if(quiz != null)
            {
                //get all attempts for the quiz
                List<Attempt> attemptList = attemptRepository.getAllAttemptsByQuiz(quiz);

                if(!attemptList.isEmpty()){

                    List<AttemptDto> attemptDtoList = new ArrayList<>();

                    for(Attempt attempt : attemptList) {
                        AttemptDto attemptDto = new AttemptDto();

                        //add values
                        attemptDto.setAttemptId(attempt.getAttemptId());
                        attemptDto.setQuizId(attempt.getQuiz().getQuizId());
                        attemptDto.setUserId(attempt.getUser().getUserId());
                        attemptDto.setFullName(attempt.getUser().getFirstName().concat(" ")+attempt.getUser().getLastName());
                        attemptDto.setCorrectCount(attempt.getCorrectCount());
                        attemptDto.setScore(attempt.getScore());
                        attemptDto.setAttemptTime(dateService.getStringDateFrmDate(attempt.getAttemptTime()));

                        //add to list
                        attemptDtoList.add(attemptDto);

                    }

                    return attemptDtoList;
                }
            }

        return  null;
    }

    //get Top 5 attempts by score
    public List<Attempt> getTopFiveAttempts(){
        return attemptRepository.findFirst5ByOrderByScoreDesc();
    }

    //get top 5 attempt dtos
    public List<AttemptDto> getTopFiveAttemptDtos(){

        Quiz quiz = quizServiceImpl.getAllQuizes().get(0);

        if(quiz != null)
        {
            //get top 5 attempts as obj list
            List<Attempt> attemptList = getTopFiveAttempts();

            if(!attemptList.isEmpty()){

                List<AttemptDto> attemptDtoList = new ArrayList<>();

                for(Attempt attempt : attemptList) {
                    AttemptDto attemptDto = new AttemptDto();

                    //adding values to dto
                    attemptDto.setAttemptId(attempt.getAttemptId());
                    attemptDto.setQuizId(attempt.getQuiz().getQuizId());
                    attemptDto.setUserId(attempt.getUser().getUserId());
                    attemptDto.setFullName(attempt.getUser().getFirstName());
                    attemptDto.setLastName(attempt.getUser().getLastName());
                    attemptDto.setCorrectCount(attempt.getCorrectCount());
                    attemptDto.setScore(attempt.getScore());
                    attemptDto.setAttemptTime(dateService.getStringDateFrmDate(attempt.getAttemptTime()));

                    //addto list
                    attemptDtoList.add(attemptDto);

                }

                return attemptDtoList;
            }
        }

        return  null;
    }


    //Method for get users count who are taken the quiz
    public int getUserCount_quizTaken(){

        //get all attempts
        List<Attempt> attemptList = getAllAttempts();

        //check result
        if(!attemptList.isEmpty()){
            List<User> usersList = new ArrayList<>();

            for(Attempt attempt : attemptList){
                User user = attempt.getUser();

                if(!usersList.contains(user)){
                    usersList.add(user);
                }
            }

            return usersList.size();
        }

        return 0;
    }

    //save attempt by attempt dto
    @Transactional
    public Attempt saveAttemptByAttemptDto(AttemptDto attemptDto){

        //check dto
        if(attemptDto.getUserId() != null){

            //get current date and time
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();

            //get user
            User user = userServiceImpl.findById(attemptDto.getUserId())
                    .orElseThrow(() -> new UserNotFoundException("User not Found!"));

            Quiz quiz = quizServiceImpl.getAllQuizes().get(0);

            //calculate total and round it
            float totalScore = calculateTotalScore(attemptDto.getCorrectCount());
            int rounded_totalScore = (int) Math.round(totalScore);

            if(user != null && quiz != null) {

                Attempt attempt = new Attempt();

                //set values
                attempt.setAttemptTime(date);
                attempt.setQuiz(quiz);
                attempt.setUser(user);
                attempt.setCorrectCount(attemptDto.getCorrectCount());
                attempt.setScore(rounded_totalScore);

                Attempt savedAttempt = attemptRepository.save(attempt);

                //check result
                if(savedAttempt != null){

                    List<AttemptAnswerDto> attemptAnswerDtos = attemptDto.getAttemptAnswerDtos();

                    boolean isAnswersSaved = attemptAnswerServiceImpl.saveAttemptAnswerByDtoList(attemptAnswerDtos, savedAttempt);

                            if(isAnswersSaved){
                                return savedAttempt;
                            }
                            else{
                                return null;
                            }
                }
            }

        }

        return null;
    }

    //method for calculate total
    public float calculateTotalScore(int correctCount){

        if(correctCount > 0){
            float percentage = ((float) correctCount) / ((float) 20);

            return percentage * 100;

        }

        return 0.00F;
    }
}
