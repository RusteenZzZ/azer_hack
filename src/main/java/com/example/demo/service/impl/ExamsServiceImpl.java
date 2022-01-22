package com.example.demo.service.impl;

import com.example.demo.dto.Exam;
import com.example.demo.entity.Exams;
import com.example.demo.repository.ExamsRepo;
import com.example.demo.service.ExamsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExamsServiceImpl implements ExamsService {

    private final ExamsRepo examsRepo;

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
            System.out.println(e.toString());
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
            return new Error(e.toString());
        }
    }
}
