package com.OnbaordWeb.Dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttemptAnswerDto {

    private Long attemptAnswerId;

    private Long answerId;

    private String answerNo;

    private Integer quizId;

    private Integer questionId;

    private Integer attemptId;
}
