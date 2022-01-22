package com.example.demo.service.impl;

import com.example.demo.dto.Exam;
import com.example.demo.dto.ExamQuestion;
import com.example.demo.entity.Exams;
import com.example.demo.entity.Questions;
import com.example.demo.entity.Users;
import com.example.demo.entity.UsersExams;
import com.example.demo.enums.ExamStatus;
import com.example.demo.repository.ExamsRepo;
import com.example.demo.repository.QuestionsRepo;
import com.example.demo.repository.UsersExamsRepo;
import com.example.demo.repository.UsersRepo;
import com.example.demo.service.ExamsService;
import com.example.demo.service.QuestionsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExamsServiceImpl implements ExamsService {

    private final QuestionsService questionsService;

    private final ExamsRepo examsRepo;
    private final UsersRepo usersRepo;
    private final UsersExamsRepo usersExamRepo;
    private final QuestionsRepo questionsRepo;

    @Override
    public Object getExams() {
        try {
            List<Exams> exams = this.examsRepo.findAll();

            List<Exam> dtoExams = exams
                    .stream()
                    .map(exam -> new Exam(
                            exam.getId(),
                            exam.getTitle(),
                            exam.getNumOfQuestions(),
                            exam.getNumOfParticipated(),
                            exam.getRate(),
                            exam.getAverageScore(),
                            exam.getDifficulty().toString(),
                            exam.getDescription(),
                            exam.getTopic().getTitle(),
                            exam.getIsPublic()
                    ))
                    .collect(Collectors.toList());

            return dtoExams;
        } catch (Exception e) {
            System.out.println(e);
            return new Error(e.toString());
        }
    }

    @Override
    public Object getExamsByTopic(Long topicId) {
        try {
            List<Exams> exams = this.examsRepo.findAll();

            List<Exams> filteredExams = exams
                    .stream()
                    .filter(exam -> exam.getTopic().getId() == topicId)
                    .collect(Collectors.toList());

            List<Exam> filteredDtoExams = filteredExams
                    .stream()
                    .map(exam -> new Exam(
                            exam.getId(),
                            exam.getTitle(),
                            exam.getNumOfQuestions(),
                            exam.getNumOfParticipated(),
                            exam.getRate(),
                            exam.getAverageScore(),
                            exam.getDescription(),
                            exam.getDifficulty().toString(),
                            exam.getTopic().getTitle(),
                            exam.getIsPublic()
                    ))
                    .collect(Collectors.toList());

            return filteredDtoExams;
        } catch (Exception e) {
            System.out.println(e);
            return new Error(e.toString());
        }
    }

    @Override
    public Object assignExamToUser(Long examId, String token) {
        try {
            Exams examById = this.examsRepo.getById(examId);
            if(examById == null) {
                System.out.println(String.format("Could not find an exam with such %s id", examId.toString()));
                return new Error(String.format("Could not find an exam with such %s id", examId.toString()));
            }

            Integer numOfQuestions = examById.getNumOfQuestions();

            Users userByToken = this.usersRepo.findByToken(token);
            if(userByToken == null) {
                System.out.println(String.format("Could not find a user with such %s token", token));
                return new Error(String.format("Could not find a user with such %s token", token));
            }

//            List<Questions> questions = this.questionsRepo.findAll();
//            List<ExamQuestion> examQuestions = questions.stream()
//                    .filter(question -> question.getCategory().getId())

            List<Questions> questions = (List<Questions>) this.questionsService.getQuestionsByExamId(examId);
            if(questions == null) {
                System.out.println("Could not find questions with given examId");
                return new Error("Could not find questions with given examId");
            }

            Collections.shuffle(questions);

            List<ExamQuestion> examQuestions = new ArrayList();

            for(int i = 0; i < numOfQuestions; i++) {
                Questions question = questions.get(i);
                examQuestions.add(new ExamQuestion(
                        question.getId(),
                        question.getType().toString(),
                        question.getPenalty(),
                        question.getCoefficient(),
                        question.getTitle(),
                        question.getOptions()
                ));
            }

            UsersExams usersExams = new UsersExams(
                    examById,
                    userByToken,
                    ExamStatus.NOT_STARTED
            );

            this.usersExamRepo.save(usersExams);
            return examQuestions;
        } catch (Exception e) {
            System.out.println(e);
            return new Error(e.toString());
        }
    }
}
