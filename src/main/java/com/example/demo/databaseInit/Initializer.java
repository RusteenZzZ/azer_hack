package com.example.demo.databaseInit;

import com.example.demo.entity.Categories;
import com.example.demo.entity.Exams;
import com.example.demo.entity.Topics;
import com.example.demo.entity.Users;
import com.example.demo.enums.ExamDifficulty;
import com.example.demo.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Initializer implements CommandLineRunner {

    private final UsersRepo usersRepo;
    private final TopicsRepo topicsRepo;
    private final ExamsRepo examsRepo;
    private final CategoriesRepo categoriesRepo;
    private final QuestionsRepo questionsRepo;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Users user1 = new Users(
                "robert",
                "robert@gmail.com",
                passwordEncoder.encode("password")
        );
        this.usersRepo.save(user1);

        Topics topic1 = new Topics(
                "C"
        );
        Topics topic2 = new Topics(
                "Python"
        );
        this.topicsRepo.save(topic1);
        this.topicsRepo.save(topic2);

        Exams exam1 = new Exams(
                "C threads",
                3,
                1,
                2.0F,
                2.5F,
                "You will not pass this exam",
                ExamDifficulty.HIGH,
                topic1,
                true
        );
        Exams exam2 = new Exams(
                "Java",
                3,
                1,
                5.0F,
                3.0F,
                "Go ahead!",
                ExamDifficulty.LOW,
                topic2,
                true
        );
        this.examsRepo.save(exam1);
        this.examsRepo.save(exam2);
//
//        Categories category1


    }
}
