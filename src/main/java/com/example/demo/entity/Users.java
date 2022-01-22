package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Users {
    @Id
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String token;
    @OneToMany(mappedBy = "user")
    private Set<Answers> answers = new HashSet<>();
    @OneToMany(mappedBy = "user")
    private Set<UsersExams> studentExams = new HashSet<>();
}
