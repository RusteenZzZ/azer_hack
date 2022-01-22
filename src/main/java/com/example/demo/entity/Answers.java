package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Answers {
    @Id
    private Long id;
    private String value;
    private Boolean isCorrect;
    @ManyToOne
    private Users user;
    @ManyToOne
    private Questions question;
}
