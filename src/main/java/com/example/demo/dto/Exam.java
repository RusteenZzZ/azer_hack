package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Exam {
    private Long id;
    private String title;
    private Integer numOfQuestions;
    private Integer numOfParticipated;
    private Float rate;
    private Float averageScore;
    private String difficulty;
    private String description;
    private String topicTitle;
    private Boolean isPublic;
}
