package com.example.demo.databaseInit;

import com.example.demo.entity.*;
import com.example.demo.enums.ExamDifficulty;
import com.example.demo.enums.QuestionType;
import com.example.demo.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
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
                topic1
        );
        Exams exam2 = new Exams(
                "Python basics",
                3,
                1,
                5.0F,
                3.0F,
                "Go ahead!",
                ExamDifficulty.LOW,
                topic2
        );
        this.examsRepo.save(exam1);
        this.examsRepo.save(exam2);

        Categories category1 = new Categories(
                "Python data types",
                topic2
        );
        Categories category2 = new Categories(
                "Python tuples",
                topic2
        );
        Categories category3 = new Categories(
                "Python functions",
                topic2
        );
        Categories category4 = new Categories(
                "C data types",
                topic1
        );
        Categories category5 = new Categories(
                "C arrays",
                topic1
        );
        Categories category6 = new Categories(
                "C functions",
                topic1
        );
        this.categoriesRepo.save(category1);
        this.categoriesRepo.save(category2);
        this.categoriesRepo.save(category3);
        this.categoriesRepo.save(category4);
        this.categoriesRepo.save(category5);
        this.categoriesRepo.save(category6);

        Questions question1 = new Questions(
                QuestionType.RADIO,
                "Slice",
                0f,
                1f,
                "Which of the following data types is not supported in python?",
                "https://www.w3schools.com/python/python_variables.asp",
                "Numbers|||String|||List|||Slice",
                category1

        );
        Questions question2 = new Questions(
                QuestionType.RADIO,
                "Generics",
                0f,
                1f,
                "Which of the following data types is not supported in python?",
                "https://www.w3schools.com/python/python_variables.asp",
                "Numbers|||String|||Generics|||List",
                category1
        );
        Questions question3 = new Questions(
                QuestionType.CHECKBOX,
                "A tuple is another sequence data type that is similar to the list|||A tuple consists of a number of values separated by commas.|||Unlike lists, however, tuples are enclosed within parentheses.",
                0f,
                1f,
                "Which of the following is correct about tuples in python?",
                "w3schools.com/python/python_tuples.asp",
                "A tuple is another sequence data type that is similar to the list|||A tuple consists of a number of values separated by commas.|||Unlike lists, however, tuples are enclosed within parentheses.",
                category2
        );
        Questions question4 = new Questions(
                QuestionType.TEXT,
                "def x():",
                0f,
                1f,
                "How to initialize function 'x' in Python with null parameter?",
                "In Python the functions are defined with def: def x(param, param, ...):. You can learn more about functions here: w3schools.com/python/python_functions.asp",
                null,
                category3
        );
        Questions question5 = new Questions(
                QuestionType.RADIO,
                "False",
                0f,
                1f,
                "We can write function's default parameters first.",
                "w3schools.com/python/python_functions.asp",
                "True|||False",
                category3
        );
        Questions question6 = new Questions(
                QuestionType.RADIO,
                "True",
                0f,
                1f,
                "We need to initialize the type of parameter in function.",
                "Alike Python language, C language requires parameter type initialization. https://www.tutorialspoint.com/cprogramming/c_functions.htm",
                "True|||False",
                category6
        );
        Questions question7 = new Questions(
                QuestionType.CHECKBOX,
                "short int x;|||byte x;",
                0f,
                1f,
                "Which of the following are not valid declarations in C?",
                null,
                "short int x;|||byte x;|||short x;|||unsigned short x;",
                category4
        );
        Questions question8 = new Questions(
                QuestionType.RADIO,
                "short int x;",
                0f,
                1f,
                "Which of the following is not a valid declaration in C?",
                null,
                "short int x;|||int x;|||short x;|||unsigned short x;",
                category4
        );
        Questions question9 = new Questions(
                QuestionType.RADIO,
                "Base address of the array",
                0f,
                1f,
                "In C, if we pass an array as an argument to a function, what actually get passed?",
                null,
                "Address of the last element of array|||Base address of the array|||Value of elements in array|||First element of the array",
                category5
        );
        Questions question10 = new Questions(
                QuestionType.TEXT,
                "unsigned int",
                0f,
                1f,
                "What is the most appropriate type to initialize the positive integer?",
                null,
                null,
                category4
        );
        this.questionsRepo.save(question1);
        this.questionsRepo.save(question2);
        this.questionsRepo.save(question3);
        this.questionsRepo.save(question4);
        this.questionsRepo.save(question5);
        this.questionsRepo.save(question6);
        this.questionsRepo.save(question7);
        this.questionsRepo.save(question8);
        this.questionsRepo.save(question9);
        this.questionsRepo.save(question10);

    }
}