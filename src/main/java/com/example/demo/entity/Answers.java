package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Answers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;
    private Boolean isCorrect;
    @ManyToOne
    private Users user;
    @ManyToOne
    private Questions question;
}
