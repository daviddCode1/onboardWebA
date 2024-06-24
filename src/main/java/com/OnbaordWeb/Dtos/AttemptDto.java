package com.OnbaordWeb.Dtos;

import com.OnbaordWeb.Models.AttemptAnswer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AttemptDto {

    private Long attemptId;

    private String attemptTime;

    private Long userId;

    private String fullName;

    private String lastName;

    private Long quizId;

    private int correctCount;

    private double score;

    private List<AttemptAnswerDto> attemptAnswerDtos;
}
