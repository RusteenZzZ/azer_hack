package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExamQuestion {
    private Long id;
    private String type;
    private Float penalty;
    private Float coefficient;
    private String title;
    private String options;
}
