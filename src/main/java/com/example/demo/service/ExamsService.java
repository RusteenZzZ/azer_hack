package com.example.demo.service;

import com.example.demo.dto.CreateExam;
import com.example.demo.dto.ExamSubmission;

public interface ExamsService {

    public Object getExams();

    public Object getExamById(Long examId);

    public Object getUsersExams(String token);

    public Object getExamsByTopic(Long topicId);

    public Object createExam(String token, CreateExam createExam);

    public Object assignExamToUser(Long examId, String token);

    public Object getExamQuestions(Long usersExamId);

    public Object getExamReview(Long usersExamsId);

    public Object submitExam(String token, Long usersExamId, ExamSubmission examSubmission);
}
