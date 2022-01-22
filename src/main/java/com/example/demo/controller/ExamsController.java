package com.example.demo.controller;

import com.example.demo.service.impl.ExamsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ExamsController {

    private final ExamsServiceImpl examsService;

    @GetMapping("exams")
    public Object getExams(){
        Object res = this.examsService.getExams();

        return res;
    }

    @GetMapping("topics/{topic_id}/exams")
    public Object getExamsByTopic(@PathVariable("topic_id") Long topicId){
        Object res = this.examsService.getExamsByTopic(topicId);

        return res;
    }

//    @PostMapping("exams/{exam_id}")
//    public Object assignExamToUser(@RequestHeader)
}
