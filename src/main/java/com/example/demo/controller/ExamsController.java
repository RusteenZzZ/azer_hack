package com.example.demo.controller;

import com.example.demo.service.impl.ExamsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ExamsController {

    private final ExamsServiceImpl examsService;


}
