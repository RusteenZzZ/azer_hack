package com.example.demo.entity;

import com.example.demo.enums.QuestionType;
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
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(
            EnumType.STRING
    )
    private QuestionType type;
    private String answer;
    private Float penalty;
    private Float coefficient;
    private String title;
    private String suggestion;
    private String options;
    @ManyToOne()
    private Categories category;
    @OneToMany(mappedBy = "question")
    private Set<UsersExamsQuestions> usersExamsQuestions = new HashSet<>();

    public Questions(QuestionType type,
                     String answer,
                     Float penalty,
                     Float coefficient,
                     String title,
                     String suggestion,
                     String options,
                     Categories category) {
        this.type = type;
        this.answer = answer;
        this.penalty = penalty;
        this.coefficient = coefficient;
        this.title = title;
        this.suggestion = suggestion;
        this.options = options;
        this.category = category;
    }
}
