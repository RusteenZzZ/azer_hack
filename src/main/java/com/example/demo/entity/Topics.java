package com.example.demo.entity;

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
public class Topics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @OneToMany(mappedBy = "topic")
    private Set<Exams> exams = new HashSet<>();
    @OneToMany(mappedBy = "topic")
    private Set<Categories> categories = new HashSet<>();

    public Topics(String title) {
        this.title = title;
    }
}
