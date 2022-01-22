package com.example.demo.service.impl;

import com.example.demo.entity.Categories;
import com.example.demo.repository.CategoriesRepo;
import com.example.demo.service.CategoriesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepo categoriesRepo;

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
}
