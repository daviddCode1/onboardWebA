package com.OnbaordWeb.Services.Impl;

import com.OnbaordWeb.Dtos.FeedbackDto;
import com.OnbaordWeb.Exceptions.UserNotFoundException;
import com.OnbaordWeb.Models.Feedback;
import com.OnbaordWeb.Models.User;
import com.OnbaordWeb.Repositories.FeedbackRepository;
import com.OnbaordWeb.Services.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserServiceImpl userServiceImpl;


    //save
    @Override
    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    //find all
    @Override
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    //find by id
    @Override
    public Optional<Feedback> findById(Long id) {
        return feedbackRepository.findById(id);
    }

    //get all feddback dto list
    public List<FeedbackDto> allFeedbackDtos(){

        List<Feedback> feedbackList = findAll();

        if(!feedbackList.isEmpty()){
            List<FeedbackDto> feedbackDtoList = new ArrayList<>();

            for(Feedback feedback : feedbackList){

                User user = userServiceImpl.findById(feedback.getUserId()).orElseThrow(() -> new UserNotFoundException("User not Found!"));

                FeedbackDto feedbackDto = new FeedbackDto();

                feedbackDto.setFeedbackId(feedback.getFeedbackId());
                feedbackDto.setAttemptId(feedback.getAttemptId());
                feedbackDto.setUserId(feedback.getUserId());
                feedbackDto.setRating(feedback.getRating());
                feedbackDto.setDescription(feedback.getDescription());

                if(user != null){
                    feedbackDto.setFullName(user.getFirstName().concat(" ")+user.getLastName());
                }

                feedbackDtoList.add(feedbackDto);
            }

            return feedbackDtoList;
        }


        return null;
    }

    //Method for get Feedback by userId from DB
    public List<Feedback> getFeedbacksByUserId(Long userId){
        return feedbackRepository.getFeedbacksByUserId(userId);
    }

    //Method for get feedbacksDtos by userId
    public List<FeedbackDto> allFeedbackDtosByUserId(Long userId){

        List<Feedback> feedbackList = getFeedbacksByUserId(userId);

        if(!feedbackList.isEmpty()){
            List<FeedbackDto> feedbackDtoList = new ArrayList<>();

            for(Feedback feedback : feedbackList){

                FeedbackDto feedbackDto = new FeedbackDto();

                feedbackDto.setFeedbackId(feedback.getFeedbackId());
                feedbackDto.setAttemptId(feedback.getAttemptId());
                feedbackDto.setUserId(feedback.getUserId());
                feedbackDto.setRating(feedback.getRating());
                feedbackDto.setDescription(feedback.getDescription());
                

                feedbackDtoList.add(feedbackDto);
            }

            return feedbackDtoList;
        }


        return null;
    }

    //save feedback by dto
    public Feedback saveFeedBackByDto(FeedbackDto feedbackDto){

        if(feedbackDto != null){
            //get relevant user and map
            User user = userServiceImpl.findById(feedbackDto.getUserId()).orElseThrow(() -> new UserNotFoundException("User not Found!"));

            if(user != null){
                Feedback feedback = new Feedback();

                feedback.setRating(feedbackDto.getRating());
                feedback.setDescription(feedbackDto.getDescription());
                feedback.setUserId(feedbackDto.getUserId());
                feedback.setAttemptId(feedbackDto.getAttemptId());

                return save(feedback);
            }
        }

        return  null;
    }
}
