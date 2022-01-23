package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String token;
    @OneToMany(mappedBy = "user")
    private Set<UsersExams> studentExams = new HashSet<>();

    public Users(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
