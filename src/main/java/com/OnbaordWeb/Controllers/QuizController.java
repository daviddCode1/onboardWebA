package com.OnbaordWeb.Controllers;

import com.OnbaordWeb.Dtos.QuizDto;
import com.OnbaordWeb.Services.Impl.QuizServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quizzes")
public class QuizController {

    private final QuizServiceImpl quizServiceImpl;

    //find quiz by id
    @GetMapping("/quiz/{id}")
    public ResponseEntity<?> getQuizById(@PathVariable("id") Long id){

        QuizDto quizDto = quizServiceImpl.getQuizDtoById(id);

        if(quizDto != null){
            return ResponseEntity.ok(quizDto);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }




}
