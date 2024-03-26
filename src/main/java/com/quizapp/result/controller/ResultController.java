package com.quizapp.result.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.quizapp.common.exceptions.NotFoundException;
import com.quizapp.quiz.dto.QuizResponse;
import com.quizapp.result.dto.ResultDTO;
import com.quizapp.result.dto.ResultLeaderboardDTO;
import com.quizapp.result.service.IResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("result")
public class ResultController {

    @Autowired
    private IResultService resultService;

    @GetMapping("/{takeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResultDTO> getResultByTakeId(@PathVariable UUID takeId) throws JsonProcessingException {
        return ResponseEntity.ok(resultService.getResultsByTakeId(takeId));
    }

    @GetMapping("leaderboard/{quizId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResultLeaderboardDTO> getLeaderboardByQuizId(@PathVariable UUID quizId) throws NotFoundException {
        return ResponseEntity.ok(resultService.getQuizLeaderboard(quizId));
    }
}
