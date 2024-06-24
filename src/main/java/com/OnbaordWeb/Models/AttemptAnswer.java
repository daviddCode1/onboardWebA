package com.OnbaordWeb.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Setter
@Getter
@NoArgsConstructor
public class AttemptAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attemptAnswerId;

    private Long answerOptionId;

    private Long quizId;

    private Long questionId;

    private Long attemptId;
}
