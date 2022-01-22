package com.example.demo.service.impl;

import com.example.demo.entity.Categories;
import com.example.demo.entity.Exams;
import com.example.demo.entity.Questions;
import com.example.demo.repository.ExamsRepo;
import com.example.demo.repository.QuestionsRepo;
import com.example.demo.service.QuestionsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionsServiceImpl implements QuestionsService {

    private final CategoriesServiceImpl categoriesService;

    private final ExamsRepo examsRepo;
    private final QuestionsRepo questionsRepo;

    @Override
    public Object getQuestionsByExamId(Long examId) {
        Exams exam = this.examsRepo.getById(examId);
        if(exam == null) {
            System.out.println(String.format("Could not find an exam with such %s id", examId.toString()));
            return null;
        }

        List<Categories> categories = (List<Categories>) this.categoriesService.getCategoriesByTopicId(exam.getTopic().getId());
        if(categories == null) {
            System.out.println(String.format("Could not find categories with such %s topicId", exam.getTopic().getId().toString()));
            return null;
        }

        List<Long> categoryIds = categories.stream()
                .map(category -> category.getId())
                .collect(Collectors.toList());

        List<Questions> questions = (List<Questions>) this.getQuestionsByCategoryIds(categoryIds);
        if(questions == null) {
            System.out.println("Could not find questions with given categoryIds");
            return null;
        }

        return questions;
    }

    @Override
    public Object getQuestionsByCategoryIds(List<Long> categoryIds) {
        try {
            List<Questions> questions = this.questionsRepo.findAll();
            List<Questions> questionsByCategoryIds = questions.stream()
                    .filter(question -> categoryIds.contains(question.getCategory().getId()))
                    .collect(Collectors.toList());
            return questionsByCategoryIds;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
