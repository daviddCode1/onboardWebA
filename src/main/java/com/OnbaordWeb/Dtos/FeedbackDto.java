package com.OnbaordWeb.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackDto {

    private Long feedbackId;

    private Integer rating;

    private String description;

    private String fullName;

    private Long userId;

    private Long attemptId;
}
