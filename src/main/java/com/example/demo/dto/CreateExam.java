package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateExam {
    private String title;
    private Integer numOfQuestions;
    private Long topicId;
    private List<Long> categoryIds;
}
