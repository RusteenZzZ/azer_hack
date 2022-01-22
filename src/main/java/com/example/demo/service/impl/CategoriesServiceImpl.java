package com.example.demo.service.impl;

import com.example.demo.entity.Categories;
import com.example.demo.entity.Questions;
import com.example.demo.repository.CategoriesRepo;
import com.example.demo.repository.QuestionsRepo;
import com.example.demo.service.CategoriesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepo categoriesRepo;
    private final QuestionsRepo questionsRepo;

    @Override
    public Object getCategoriesByTopicId(Long topicId) {
        try {
            List<Categories> categories = this.categoriesRepo.findAll();
            List<Categories> categoriesByTopicId = categories.stream()
                    .filter(category -> category.getTopic().getId() == topicId)
                    .collect(Collectors.toList());

            return categoriesByTopicId;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public Integer getNumOfQuestions(Long categoryId) {
        try {
            Categories category = this.categoriesRepo.getById(categoryId);
            if(category == null) {
                System.out.println(String.format("Could not find category with such %s id", categoryId.toString()));
                return null;
            }

            List<Questions> questions = this.questionsRepo.findAll();

            List<Questions> questionsByCategoryId = questions.stream()
                    .filter(question -> question.getCategory().getId() == categoryId)
                    .collect(Collectors.toList());

            return questionsByCategoryId.size();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
