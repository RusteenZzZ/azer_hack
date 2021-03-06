package com.example.demo.entity;

import com.example.demo.enums.ExamDifficulty;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
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
    private Boolean isPublic;

    public Exams(String title,
                 Integer numOfQuestions,
                 Integer numOfParticipated,
                 Float rate,
                 Float averageScore,
                 String description,
                 ExamDifficulty difficulty,
                 Topics topic,
                 Boolean isPublic) {
        this.title = title;
        this.numOfQuestions = numOfQuestions;
        this.numOfParticipated = numOfParticipated;
        this.rate = rate;
        this.averageScore = averageScore;
        this.description = description;
        this.difficulty = difficulty;
        this.topic = topic;
        this.isPublic = isPublic;
    }
}
