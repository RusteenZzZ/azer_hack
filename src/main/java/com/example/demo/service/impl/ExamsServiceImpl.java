package com.example.demo.service.impl;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.enums.ExamStatus;
import com.example.demo.enums.QuestionType;
import com.example.demo.repository.*;
import com.example.demo.service.ExamsService;
import com.example.demo.service.QuestionsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExamsServiceImpl implements ExamsService {

    private final QuestionsService questionsService;

    private final ExamsRepo examsRepo;
    private final UsersRepo usersRepo;
    private final UsersExamsRepo usersExamsRepo;
    private final QuestionsRepo questionsRepo;
    private final UsersExamsQuestionsRepo usersExamsQuestionsRepo;
    private final AnswersRepo answersRepo;

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
            return new ErrorMessage(e.toString());
        }
    }

    @Override
    public Object getExamById(Long examId) {
        try {
            Optional<Exams> exam = this.examsRepo.findById(examId);
            if(exam.isEmpty()) {
                System.out.printf("Could not find an exam with such %s id%n", examId.toString());
                return new ErrorMessage(String.format("Could not find an exam with such %s id", examId.toString()));
            }

            Exams gotExam = exam.get();

            Exam dtoExam = new Exam(
                    gotExam.getId(),
                    gotExam.getTitle(),
                    gotExam.getNumOfQuestions(),
                    gotExam.getNumOfParticipated(),
                    gotExam.getRate(),
                    gotExam.getAverageScore(),
                    gotExam.getDifficulty().toString(),
                    gotExam.getDescription(),
                    gotExam.getTopic().getId(),
                    gotExam.getTopic().getTitle(),
                    gotExam.getIsPublic()
            );

            return dtoExam;
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage(e.toString());
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
            return new ErrorMessage(e.toString());
        }
    }

    @Override
    public Object assignExamToUser(Long examId, String token) {
        try {
            Optional<Exams> examById = this.examsRepo.findById(examId);

            if(examById.isEmpty()) {
                System.out.printf("Could not find an exam with such %s id%n", examId.toString());
                return new ErrorMessage(String.format("Could not find an exam with such %s id", examId.toString()));
            }
            Integer numOfQuestions = examById.get().getNumOfQuestions();

            Users userByToken = this.usersRepo.findByToken(token);
            if(userByToken == null) {
                System.out.printf("Could not find a user with such %s token%n", token);
                return new ErrorMessage(String.format("Could not find a user with such %s token", token));
            }

            List<Questions> questions = (List<Questions>) this.questionsService.getQuestionsByExamId(examId);
            if(questions == null) {
                System.out.println("Could not find questions with given examId");
                return new ErrorMessage("Could not find questions with given examId");
            }

            Collections.shuffle(questions);

            UsersExams usersExams = new UsersExams(
                    examById.get(),
                    userByToken,
                    ExamStatus.ONGOING
            );

            this.usersExamsRepo.save(usersExams);

            for(int i = 0; i < numOfQuestions; i++){
                Questions question = questions.get(i);
                this.usersExamsQuestionsRepo.save(
                        new UsersExamsQuestions(
                            usersExams,
                            question
                        )
                );
            }

            return new UsersExamsResponse(
                    usersExams.getId()
            );
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage(e.toString());
        }
    }

    @Override
    public Object getExamQuestions(Long usersExamId) {
        try {
            Optional<UsersExams> usersExam = this.usersExamsRepo.findById(usersExamId);
            if(usersExam.isEmpty()) {
                System.out.printf("Could not find a usersExam with such %s id%n", usersExamId.toString());
                return new ErrorMessage(String.format("Could not find a usersExam with such %s id", usersExamId.toString()));
            }

            Optional<Exams> examById = this.examsRepo.findById(usersExam.get().getExam().getId());
            if(examById.isEmpty()) {
                System.out.printf("Could not find an exam with such %s id%n", usersExam.get().getExam().getId().toString());
                return new ErrorMessage(String.format("Could not find an exam with such %s id", usersExam.get().getExam().getId().toString()));
            }

            List<UsersExamsQuestions> usersExamsQuestions = this.usersExamsQuestionsRepo.findAll();
            List<UsersExamsQuestions> usersExamsQuestionsByUsersExamId = usersExamsQuestions.stream()
                    .filter(question -> question.getUsersExam().getId() == usersExamId)
                    .collect(Collectors.toList());

            List<ExamQuestion> examQuestions = new ArrayList();

            for(UsersExamsQuestions userExamQuestion: usersExamsQuestionsByUsersExamId) {
                Questions question = this.questionsRepo.getById(
                        userExamQuestion.getQuestion().getId()
                );
                examQuestions.add(new ExamQuestion(
                        question.getId(),
                        question.getType().toString(),
                        question.getPenalty(),
                        question.getCoefficient(),
                        question.getTitle(),
                        question.getOptions()
                ));
            }

            return examQuestions;
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage(e.toString());
        }
    }


    @Override
    public Object getExamReview(Long usersExamsId) {
        try {
            Optional<UsersExams> usersExam = this.usersExamsRepo.findById(usersExamsId);
            if(usersExam.isEmpty()) {
                System.out.printf("Could not find usersExam with such %s id%n", usersExamsId.toString());
                return new ErrorMessage(String.format("Could not find usersExam with such %s id", usersExamsId.toString()));
            }

            List<QuestionReview> questionReviews = new ArrayList<>();

            List<UsersExamsQuestions> usersExamsQuestions = this.usersExamsQuestionsRepo.findAll();
            List<UsersExamsQuestions> usersExamsQuestionsByUsersExamId = usersExamsQuestions.stream()
                    .filter(usersExamsQuestion -> usersExamsQuestion.getUsersExam().getId() == usersExamsId)
                    .collect(Collectors.toList());

            for(UsersExamsQuestions usersExamsQuestion: usersExamsQuestionsByUsersExamId) {
                Questions question = this.questionsRepo.getById(usersExamsQuestion.getQuestion().getId());
                questionReviews.add(
                        new QuestionReview(
                                question.getId(),
                                question.getType().toString(),
                                question.getTitle(),
                                question.getOptions(),
                                question.getCategory().getId(),
                                question.getCategory().getTitle(),
                                question.getAnswer(),
                                usersExamsQuestion.getAnswer(),
                                usersExamsQuestion.getIsCorrect(),
                                question.getSuggestion()
                        )
                );
            }

            return new ExamReview(
                    usersExam.get().getExam().getTitle(),
                    usersExam.get().getScore(),
                    questionReviews
            );
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage(e.toString());
        }
    }

    @Override
    public Object submitExam(String token, Long usersExamId, ExamSubmission examSubmission) {
        try {
            Users user = this.usersRepo.findByToken(token);
            if(user == null) {
                System.out.printf("Could not find a user with such %s token%n", token);
                return new ErrorMessage(String.format("Could not find a user with such %s token", token));
            }

            Optional<UsersExams> usersExam = this.usersExamsRepo.findById(usersExamId);
            if(usersExam.isEmpty()) {
                System.out.printf("Could not find a usersExam with such %s id%n", usersExamId.toString());
                return new ErrorMessage(String.format("Could not find a usersExam with such %s id", usersExamId.toString()));
            }

            Float score = 0F;
            Float coeffs = 0F;

            List<QuestionReview> questionReviews = new ArrayList<>();

            List<UsersExamsQuestions> usersExamsQuestions = this.usersExamsQuestionsRepo.findAll();
            List<UsersExamsQuestions> usersExamsQuestionsByUsersExamId = usersExamsQuestions.stream()
                    .filter(usersExamQuestion -> usersExamQuestion.getUsersExam().getId() == usersExamId)
                    .collect(Collectors.toList());

            for(QuestionSubmission questionSubmission: examSubmission.getQuestionSubmissions()) {
                QuestionReview questionReview = new QuestionReview();
                Questions question = this.questionsRepo.getById(questionSubmission.getQuestionId());
                coeffs += question.getCoefficient();
                UsersExamsQuestions usersExamQuestion = usersExamsQuestionsByUsersExamId.stream()
                                .filter(usersExamsQuestion -> usersExamsQuestion.getQuestion().getId() == question.getId())
                                .findFirst().orElse(null);
                usersExamQuestion.setAnswer(questionSubmission.getAnswer());

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
                            usersExamQuestion.setIsCorrect(false);
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
                                usersExamQuestion.setIsCorrect(true);
                            }else {
                                score -= question.getPenalty();
                                questionReview.setIsCorrect(false);
                                usersExamQuestion.setIsCorrect(false);
                            }
                        }
                    } else {
                        if(questionSubmission.getAnswer().equals(question.getAnswer())) {
                            score += question.getCoefficient();
                            questionReview.setIsCorrect(true);
                            usersExamQuestion.setIsCorrect(true);
                        } else {
                            score -= question.getPenalty();
                            questionReview.setIsCorrect(false);
                            usersExamQuestion.setIsCorrect(false);
                        }
                    }
                } else {
                    questionReview.setIsCorrect(false);
                    usersExamQuestion.setIsCorrect(false);
                }

                questionReviews.add(questionReview);
            }

            score = (score/coeffs) * 100F;

            Exams exam = this.examsRepo.getById(usersExam.get().getExam().getId());
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

            usersExam.get().setScore(score);
            usersExam.get().setRating(examSubmission.getRating());
            usersExam.get().setStatus(ExamStatus.FINISHED);

            this.usersExamsRepo.save(usersExam.get());

            return new SubmitSuccess(true);
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage(e.toString());
        }
    }
}
