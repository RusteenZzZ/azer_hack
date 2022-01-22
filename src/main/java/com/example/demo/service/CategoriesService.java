package com.example.demo.service;

public interface CategoriesService {

    public Object getCategoriesByTopicId(Long topicId);

    public Object getNumOfQuestions(Long categoryId);
}
