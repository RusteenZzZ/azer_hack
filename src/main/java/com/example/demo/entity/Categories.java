package com.example.demo.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne()
    private Topics topic;
    @OneToMany(mappedBy = "category")
    private Set<Questions> questions = new HashSet<>();
}
