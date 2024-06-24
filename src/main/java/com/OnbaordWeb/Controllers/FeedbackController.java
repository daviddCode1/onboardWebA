package com.OnbaordWeb.Controllers;

import com.OnbaordWeb.Dtos.FeedbackDto;
import com.OnbaordWeb.Models.Feedback;
import com.OnbaordWeb.Services.Impl.FeedbackServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackServiceImpl feedbackServiceImpl;

    //save feedback
    @PostMapping("/feedback")
    public ResponseEntity<?> saveFeedback(@RequestBody FeedbackDto feedbackDto){


        try {

            Feedback savedFeedback = feedbackServiceImpl.saveFeedBackByDto(feedbackDto);

            if(savedFeedback != null) {
                return ResponseEntity.ok("Feedback Added Successfully!");
            }
            else{
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error in Feedback Saving Process!");
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    //get all feedbacks
    @GetMapping("/allFeedbacks")
    public ResponseEntity<?> getAllFeedbacks(){
         return ResponseEntity.ok(feedbackServiceImpl.findAll());
    }

    //get all feedbacks Dto
    @GetMapping("/allFeedbacksDto")
    public ResponseEntity<?> getAllFeedbacksDto(){
        return ResponseEntity.ok(feedbackServiceImpl.allFeedbackDtos());
    }

    //get feedback dtos by userId
    @GetMapping("/allFeedbacksDtoByUserId/{userId}")
    public ResponseEntity<?> getAllFeedbacksDtoByUserId(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(feedbackServiceImpl.allFeedbackDtosByUserId(userId));
    }
}
