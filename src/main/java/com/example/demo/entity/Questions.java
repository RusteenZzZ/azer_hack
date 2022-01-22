package com.example.demo.entity;

import com.example.demo.enums.QuestionType;
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
    private Set<Answers> chosenAnswers = new HashSet<>();
}
