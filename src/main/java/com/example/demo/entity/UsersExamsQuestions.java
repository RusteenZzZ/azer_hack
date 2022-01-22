package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UsersExamsQuestions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private UsersExams usersExam;
    @ManyToOne
    private Questions question;

    public UsersExamsQuestions(UsersExams usersExam, Questions question) {
        this.usersExam = usersExam;
        this.question = question;
    }
}
