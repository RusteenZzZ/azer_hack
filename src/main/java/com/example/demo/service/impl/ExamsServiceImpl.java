package com.example.demo.service.impl;

import com.example.demo.dto.*;
import com.example.demo.entity.Exams;
import com.example.demo.entity.Questions;
import com.example.demo.entity.Users;
import com.example.demo.entity.UsersExams;
import com.example.demo.enums.ExamStatus;
import com.example.demo.enums.QuestionType;
import com.example.demo.repository.ExamsRepo;
import com.example.demo.repository.QuestionsRepo;
import com.example.demo.repository.UsersExamsRepo;
import com.example.demo.repository.UsersRepo;
import com.example.demo.service.ExamsService;
import com.example.demo.service.QuestionsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.Error;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExamsServiceImpl implements ExamsService {

    private final QuestionsService questionsService;

    private final ExamsRepo examsRepo;
    private final UsersRepo usersRepo;
    private final UsersExamsRepo usersExamsRepo;
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
                            exam.getTopic().getId(),
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
    public Object getExamById(Long examId) {
        try {
            Exams exam = this.examsRepo.getById(examId);
            if(exam == null) {
                System.out.println(String.format("Could not find an exam with such %s id", examId.toString()));
                return new Error(String.format("Could not find an exam with such %s id", examId.toString()));
            }

            Exam dtoExam = new Exam(
                    exam.getId(),
                    exam.getTitle(),
                    exam.getNumOfQuestions(),
                    exam.getNumOfParticipated(),
                    exam.getRate(),
                    exam.getAverageScore(),
                    exam.getDifficulty().toString(),
                    exam.getDescription(),
                    exam.getTopic().getId(),
                    exam.getTopic().getTitle(),
                    exam.getIsPublic()
            );

            return dtoExam;
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
                            exam.getTopic().getId(),
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
                    ExamStatus.ONGOING
            );

            this.usersExamsRepo.save(usersExams);
            return examQuestions;
        } catch (Exception e) {
            System.out.println(e);
            return new Error(e.toString());
        }
    }

    @Override
    public Object submitExam(String token, Long usersExamId, ExamSubmission examSubmission) {
        try {
            Users user = this.usersRepo.findByToken(token);
            if(user == null) {
                System.out.println(String.format("Could not find a user with such %s token", token));
                return new Error(String.format("Could not find a user with such %s token", token));
            }

            UsersExams usersExam = this.usersExamsRepo.getById(usersExamId);
            if(usersExam == null) {
                System.out.println(String.format("Could not find a usersExam with such %s id", usersExamId.toString()));
                return new Error(String.format("Could not find a usersExam with such %s id", usersExamId.toString()));
            }

            Float score = 0F;
            Float coeffs = 0F;

            List<QuestionReview> questionReviews = new ArrayList<>();

            for(QuestionSubmission questionSubmission: examSubmission.getQuestionSubmissions()) {
                QuestionReview questionReview = new QuestionReview();
                Questions question = this.questionsRepo.getById(questionSubmission.getQuestionId());
                coeffs += question.getCoefficient();

                questionReview.setAnswer(question.getAnswer());
                questionReview.setGivenAnswer(questionSubmission.getAnswer());
                questionReview.setSuggestion(question.getSuggestion());

                if(questionSubmission.getAnswer().length() != 0) {
                    if(question.getType() == QuestionType.CHECKBOX) {
                        String[] answers = question.getAnswer().split("|||");
                        String[] chosenAnswers = questionSubmission.getAnswer().split("|||");

                        if(answers.length != chosenAnswers.length) {
                            score -= question.getPenalty();
                            questionReview.setIsCorrect(false);
                        } else {
                            Arrays.sort(answers);
                            Arrays.sort(chosenAnswers);

                            Boolean isCorrect = true;

                            for(int i = 0; i < answers.length; i++) {
                                if(!answers[i].equals(chosenAnswers[i])) {
                                    isCorrect = false;
                                    break;
                                }
                            }

                            if(isCorrect) {
                                score += question.getCoefficient();
                                questionReview.setIsCorrect(true);
                            }else {
                                score -= question.getPenalty();
                                questionReview.setIsCorrect(false);
                            }
                        }
                    } else {
                        if(questionSubmission.getAnswer().equals(question.getAnswer())) {
                            score += question.getCoefficient();
                            questionReview.setIsCorrect(true);
                        } else {
                            score -= question.getPenalty();
                            questionReview.setIsCorrect(false);
                        }
                    }
                } else {
                    questionReview.setIsCorrect(false);
                }

                questionReviews.add(questionReview);
            }

            score = (score/coeffs) * 100F;

            Exams exam = this.examsRepo.getById(usersExam.getExam().getId());
            Float prevAverageScore = exam.getAverageScore();
            Float prevRating = exam.getRate();

            exam.setAverageScore(
                    (prevAverageScore * exam.getNumOfParticipated() + score)
                            /(exam.getNumOfParticipated() + 1)
            );
            exam.setRate(
                    (prevRating * exam.getNumOfParticipated() + examSubmission.getRating())
                            /(exam.getNumOfParticipated() + 1)
            );

            this.examsRepo.save(exam);

            usersExam.setScore(score);
            usersExam.setRating(examSubmission.getRating());
            usersExam.setStatus(ExamStatus.FINISHED);

            this.usersExamsRepo.save(usersExam);

            return new ExamReview(
                    score,
                    questionReviews
            );
        } catch (Exception e) {
            System.out.println(e);
            return new Error(e.toString());
        }
    }
}
