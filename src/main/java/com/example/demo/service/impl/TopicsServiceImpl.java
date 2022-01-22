package com.example.demo.service.impl;

import com.example.demo.entity.Topics;
import com.example.demo.repository.TopicsRepo;
import com.example.demo.service.TopicsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TopicsServiceImpl implements TopicsService {

    private TopicsRepo topicsRepo;

    @Override
    public Object getTopics() {
        try {
            List<Topics> topics = topicsRepo.findAll();
            return topics;
        } catch (Exception e) {
            return new Error(e.toString());
        }
    }
}
