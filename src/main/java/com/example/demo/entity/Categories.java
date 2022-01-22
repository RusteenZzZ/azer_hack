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
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String suggestion;
    @ManyToOne()
    private Topics topic;
    @OneToMany(mappedBy = "category")
    private Set<Questions> questions = new HashSet<>();

    public Categories(String title, Topics topic, String suggestion) {
        this.title = title;
        this.topic = topic;
        this.suggestion = suggestion;
    }
}
