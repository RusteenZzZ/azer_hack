package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionReview {
    private Long questionId;
    private String title;
    private Long categoryId;
    private String categoryTitle;
    private String answer;
    private String givenAnswer;
    private Boolean isCorrect;
    private String suggestion;
}
