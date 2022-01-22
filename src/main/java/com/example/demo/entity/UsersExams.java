package com.example.demo.entity;

import com.example.demo.enums.ExamStatus;

import javax.persistence.*;

@Entity
public class UsersExams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Exams exam;
    @ManyToOne
    private Users user;
    private Float rating;
    private Float score;
    @Enumerated(
            EnumType.STRING
    )
    private ExamStatus status;
}
