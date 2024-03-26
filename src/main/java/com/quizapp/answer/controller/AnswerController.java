package com.quizapp.answer.controller;

import com.quizapp.answer.dto.AnswerResponse;
import com.quizapp.answer.dto.CreateAnswerRequest;
import com.quizapp.answer.service.IAnswerService;
import com.quizapp.common.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("answer")
public class AnswerController {

    @Autowired
    private IAnswerService answerService;

    @PostMapping
    public ResponseEntity<?> saveAnswer(@RequestBody CreateAnswerRequest answerRequest) throws NotFoundException {
        answerService.saveAnswer(answerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", "Quiz created successfully"));
    }

    @GetMapping("/{questionId}")
    public List<AnswerResponse> getAllAnswersByQuestionId(@PathVariable UUID questionId){
        return answerService.getAllAnswersByQuestionId(questionId);
    }
}
