package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExamOfUser {
    private Long id;
    private String title;
    private Float score;
    private String difficulty;
    private Date createdAt;
}
