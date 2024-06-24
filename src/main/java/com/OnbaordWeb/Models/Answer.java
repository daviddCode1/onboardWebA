package com.OnbaordWeb.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    private String correctAnswerNo;

    @OneToOne(mappedBy = "answer", cascade = CascadeType.ALL)
    private Question question;

}
