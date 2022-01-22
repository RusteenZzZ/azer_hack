package com.example.demo.entity;

import com.example.demo.enums.ExamDifficulty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Exams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer numOfQuestions;
    private Integer numOfParticipated;
    private Float rate;
    private Float averageScore;
    @Column(
        columnDefinition = "varchar(3000) default 'To be filled...'"
    )
    private String description;
    @Enumerated(
            EnumType.STRING
    )
    private ExamDifficulty difficulty;
    @ManyToOne
    private Topics topic;
    @OneToMany(mappedBy = "exam")
    private Set<UsersExams> studentExams = new HashSet<>();

    public Exams(String title,
                 Integer numOfQuestions,
                 Integer numOfParticipated,
                 Float rate,
                 Float averageScore,
                 String description,
                 ExamDifficulty difficulty,
                 Topics topic) {
        this.title = title;
        this.numOfQuestions = numOfQuestions;
        this.numOfParticipated = numOfParticipated;
        this.rate = rate;
        this.averageScore = averageScore;
        this.description = description;
        this.difficulty = difficulty;
        this.topic = topic;
    }
}
