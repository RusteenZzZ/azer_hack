package com.example.demo.service.impl;

import com.example.demo.dto.Category;
import com.example.demo.dto.ErrorMessage;
import com.example.demo.dto.Topic;
import com.example.demo.entity.Categories;
import com.example.demo.entity.Topics;
import com.example.demo.repository.CategoriesRepo;
import com.example.demo.repository.TopicsRepo;
import com.example.demo.service.TopicsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TopicsServiceImpl implements TopicsService {

    private final CategoriesRepo categoriesRepo;
    private final TopicsRepo topicsRepo;

    private final CategoriesServiceImpl categoriesService;

    @Override
    public Object getTopics() {
        try {
            List<Categories> categories = this.categoriesRepo.findAll();

            List<Topics> topics = topicsRepo.findAll();
            List<Topic> dtoTopics = topics
                    .stream()
                    .map(topic -> new Topic(
                            topic.getId(),
                            topic.getTitle(),
                            categories.stream()
                                    .filter(category -> category.getTopic().getId() == topic.getId())
                                    .map(category -> new Category(
                                            category.getId(),
                                            category.getTitle(),
                                            category.getSuggestion(),
                                            this.categoriesService.getNumOfQuestions(category.getId())
                                    ))
                                    .collect(Collectors.toList())
                    ))
                    .collect(Collectors.toList());
            return dtoTopics;
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage(e.toString());
        }
    }
}
