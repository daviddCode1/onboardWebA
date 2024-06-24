package com.OnbaordWeb.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    private Integer questionNo;

    private String description;

    @ManyToOne
    @JoinColumn(name = "quiz_Id")
    private Quiz quiz;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<AnswerOption> answerOptions;

    @OneToOne
    @JoinColumn(name = "answer_Id")
    private Answer answer;
}
