package com.example.demo.databaseInit;

import com.example.demo.entity.*;
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
                "Python basics",
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

        Categories category1 = new Categories(

        );
        Categories category2 = new Categories(

        );
        Categories category3 = new Categories(

        );
        Categories category4 = new Categories(

        );
        Categories category5 = new Categories(

        );
        Categories category6 = new Categories(

        );
        this.categoriesRepo.save(category1);
        this.categoriesRepo.save(category2);
        this.categoriesRepo.save(category3);
        this.categoriesRepo.save(category4);
        this.categoriesRepo.save(category5);
        this.categoriesRepo.save(category6);

        Questions question1 = new Questions(

        );
        Questions question2 = new Questions(

        );
        Questions question3 = new Questions(

        );
        Questions question4 = new Questions(

        );
        Questions question5 = new Questions(

        );
        Questions question6 = new Questions(

        );
        Questions question7 = new Questions(

        );
        Questions question8 = new Questions(

        );
        Questions question9 = new Questions(

        );
        Questions question10 = new Questions(

        );
        this.usersRepo.save(question1);
        this.usersRepo.save(question2);
        this.usersRepo.save(question3);
        this.usersRepo.save(question4);
        this.usersRepo.save(question5);
        this.usersRepo.save(question6);
        this.usersRepo.save(question7);
        this.usersRepo.save(question8);
        this.usersRepo.save(question9);
        this.usersRepo.save(question10);

    }
}
