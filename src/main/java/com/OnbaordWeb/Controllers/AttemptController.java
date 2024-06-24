package com.OnbaordWeb.Controllers;

import com.OnbaordWeb.Dtos.AttemptDto;
import com.OnbaordWeb.Models.Attempt;
import com.OnbaordWeb.Services.Impl.AttemptServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attempts")
public class AttemptController {

    private final AttemptServiceImpl attemptServiceImpl;

    //save
    @PostMapping("/attempt")
    public ResponseEntity<?> saveAttempt(@RequestBody AttemptDto attemptDto){


        try {

            Attempt savedAttempt = attemptServiceImpl.saveAttemptByAttemptDto(attemptDto);

            if(savedAttempt != null) {
                return ResponseEntity.ok(savedAttempt.getAttemptId());
            }
            else{
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error in Results Saving Process!");
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    //get attempts by userId
    @GetMapping("/attemptList/{userId}")
    public ResponseEntity<?> getAllAttemptDtosById(@PathVariable("userId") Long userId){

        List<AttemptDto> attempts = attemptServiceImpl.getAllAttemptsByUserId(userId);

        return ResponseEntity.ok(attempts);
    }

    //get all attempts
    @GetMapping("/allAttemptDtoList")
    public ResponseEntity<?> getAllAttemptDtos(){

        List<AttemptDto> attempts = attemptServiceImpl.getAllAttemptsListAsDto();

        return ResponseEntity.ok(attempts);
    }

    //get top five attempts
    @GetMapping("/topFiveAttempts")
    public ResponseEntity<?> getTopFiveAttempts(){

        List<AttemptDto> attempts = attemptServiceImpl.getTopFiveAttemptDtos();

        return ResponseEntity.ok(attempts);
    }

    //get quiz taken count
    @GetMapping("/quizTakenUserCount")
    public ResponseEntity<?> getUserCount_quizTaken(){

        int userCount = attemptServiceImpl.getUserCount_quizTaken();

        return ResponseEntity.ok(userCount);
    }



}
