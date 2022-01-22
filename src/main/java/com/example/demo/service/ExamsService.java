package com.example.demo.service;

public interface ExamsService {

    public Object getExams();

    public Object getExamsByTopic(Long topicId);

    public Object assignExamToUser(Long examId, String token);
}
