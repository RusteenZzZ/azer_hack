package com.example.demo.entity;

import com.example.demo.enums.ExamStatus;
import lombok.*;

import javax.persistence.*;

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

    public UsersExams(Exams exam,
                      Users user,
                      ExamStatus status) {
        this.exam = exam;
        this.user = user;
        this.status = status;
    }
}
