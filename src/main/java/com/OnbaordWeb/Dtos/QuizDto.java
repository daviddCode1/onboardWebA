package com.OnbaordWeb.Dtos;

import com.OnbaordWeb.Models.Question;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuizDto {


    private Long quizId;

    private Integer quizNo;

    private String name;

    private Double passScore;

    private List<Question> questions;
}
