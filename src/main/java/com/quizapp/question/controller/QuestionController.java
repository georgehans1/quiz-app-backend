package com.quizapp.question.controller;

import com.quizapp.common.exceptions.NotFoundException;
import com.quizapp.question.dto.CreateQuestionRequest;
import com.quizapp.question.dto.QuestionResponse;
import com.quizapp.question.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private IQuestionService questionService;

    @PostMapping
    public ResponseEntity<?> saveQuestion(@RequestBody CreateQuestionRequest createQuestionRequest) throws NotFoundException {
        questionService.saveQuestion(createQuestionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", "Quiz created successfully"));
    }

    @GetMapping("/{quizId}")
    public List<QuestionResponse> getAllQuestionsByQuizId(@PathVariable UUID quizId){
        return questionService.getAllQuestionsByQuizId(quizId);
    }
}
