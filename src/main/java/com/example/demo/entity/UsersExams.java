package com.example.demo.entity;

import com.example.demo.enums.ExamStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
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
    private Date createdAt;
    @OneToMany(mappedBy = "usersExam")
    private Set<UsersExamsQuestions> questions = new HashSet<>();

    public UsersExams(Exams exam,
                      Users user,
                      ExamStatus status) {
        this.exam = exam;
        this.user = user;
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
