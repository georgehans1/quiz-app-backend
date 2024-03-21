package com.quizapp.take.controller;

import com.quizapp.common.exceptions.NotFoundException;
import com.quizapp.quiz.dto.QuizCreateRequest;
import com.quizapp.take.dto.TakeCreateRequest;
import com.quizapp.take.dto.UserTakesDTO;
import com.quizapp.take.service.ITakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("take")
public class TakeController {

    @Autowired
    private ITakeService iTakeService;

    @GetMapping("/{userId}/{quizId}")
    public UserTakesDTO checkUserTakes(@PathVariable UUID userId, @PathVariable UUID quizId){
        return iTakeService.checkUserTake(userId, quizId);
    }

}
