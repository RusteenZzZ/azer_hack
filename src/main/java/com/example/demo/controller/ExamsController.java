package com.example.demo.controller;

import com.example.demo.service.impl.ExamsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ExamsController {

    private final ExamsServiceImpl examsService;

    @GetMapping("topics/{topic_id}/exams")
    public Object getExamsByTopic(@PathVariable("topic_id") Long topicId){
        Object res = this.examsService.getExamsByTopic(topicId);

        return res;
    }

//    @PostMapping("exams/{exam_id}")
//    public Object assingnExamToUser(@Header)
}
