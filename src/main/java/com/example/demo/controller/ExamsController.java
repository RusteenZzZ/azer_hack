package com.example.demo.controller;

import com.example.demo.dto.CreateExam;
import com.example.demo.dto.ErrorMessage;
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

    @GetMapping("users-exams")
    public Object getUsersExams(
            @RequestHeader("Authorization") String token
    ) {
        String[] strings = token.split(" ");
        if(strings.length != 2) {
            return new ErrorMessage("Wrong authorization header structure!!!");
        }
        Object res = this.examsService.getUsersExams(strings[1]);

        return res;
    }

    @GetMapping("topics/{topic_id}/exams")
    public Object getExamsByTopic(
            @PathVariable("topic_id") Long topicId
    ) {
        Object res = this.examsService.getExamsByTopic(topicId);

        return res;
    }

    @PostMapping("exams")
    public Object createExam(
            @RequestHeader("Authorization") String token,
            @RequestBody CreateExam createExam
    ) {
        String[] strings = token.split(" ");
        if(strings.length != 2) {
            return new ErrorMessage("Wrong authorization header structure!!!");
        }
        Object res = this.examsService.createExam(strings[1], createExam);

        return res;
    }

    @PostMapping("exams/{exam_id}")
    public Object assignExamToUser(
            @RequestHeader("Authorization") String token,
            @PathVariable("exam_id") Long examId
    ) {
        String[] strings = token.split(" ");
        if(strings.length != 2) {
            return new ErrorMessage("Wrong authorization header structure!!!");
        }
        Object res = this.examsService.assignExamToUser(
                examId,
                strings[1]
        );

        return res;
    }

    @GetMapping("users-exams/{users_exams_id}")
    public Object getExamQuestions(
            @PathVariable("users_exams_id") Long usersExamsId
    ) {
        Object res = this.examsService.getExamQuestions(usersExamsId);

        return res;
    }

    @GetMapping("users-exams/{users_exams_id}/review")
    public Object getExamReview(
            @PathVariable("users_exams_id") Long usersExamsId
    ) {
        Object res = this.examsService.getExamReview(usersExamsId);

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
            return new ErrorMessage("Wrong authorization header structure!!!");
        }
        Object res = this.examsService.submitExam(
                strings[1],
                usersExamId,
                examSubmission
        );

        return res;
    }
}
