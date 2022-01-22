package com.example.demo.controller;

import com.example.demo.dto.ExamSubmission;
import com.example.demo.service.impl.ExamsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ExamsController {

    private final ExamsServiceImpl examsService;

    @GetMapping("exams")
    public Object getExams() {
        Object res = this.examsService.getExams();

        return res;
    }

    @GetMapping("exams/{exam_id}")
    public Object getExamById(
            @PathVariable("exam_id") Long examId
    ) {
        Object res = this.examsService.getExamById(examId);

        return res;
    }

    @GetMapping("topics/{topic_id}/exams")
    public Object getExamsByTopic(
            @PathVariable("topic_id") Long topicId
    ) {
        Object res = this.examsService.getExamsByTopic(topicId);

        return res;
    }

    @PostMapping("exams/{exam_id}")
    public Object assignExamToUser(
            @RequestHeader("Authorization") String token,
            @PathVariable("exam_id") Long examId
    ) {
        String[] strings = token.split(" ");
        if(strings.length != 2) {
            return new Error("Wrong authorization header structure!!!");
        }
        Object res = this.examsService.assignExamToUser(
                examId,
                strings[1]
        );

        return res;
    }

    @PatchMapping("exams/{users_exam_id}")
    public Object submitExam(
            @RequestHeader("Authorization") String token,
            @PathVariable("users_exam_id") Long usersExamId,
            @RequestBody() ExamSubmission examSubmission
    ) {
        String[] strings = token.split(" ");
        if(strings.length != 2) {
            return new Error("Wrong authorization header structure!!!");
        }
        Object res = this.examsService.submitExam(
                strings[1],
                usersExamId,
                examSubmission
        );

        return res;
    }
}
