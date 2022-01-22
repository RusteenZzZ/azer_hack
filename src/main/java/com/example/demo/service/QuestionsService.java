package com.example.demo.service;

import java.util.List;

public interface QuestionsService {

    public Object getQuestionsByExamId(Long examId);

    public Object getQuestionsByCategoryIds(List<Long> categoryIds);
}
