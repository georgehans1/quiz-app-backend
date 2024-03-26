package com.quizapp.rating.controller;

import com.quizapp.common.exceptions.NotFoundException;
import com.quizapp.rating.dto.CreateRatingRequest;
import com.quizapp.rating.dto.QuizRatingDTO;
import com.quizapp.rating.dto.RatingDTO;
import com.quizapp.rating.service.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("rating")
public class RatingController {

    @Autowired
    private IRatingService ratingService;

    @PostMapping
    public ResponseEntity<?> saveRating(@RequestBody CreateRatingRequest createRatingRequest) throws NotFoundException {
        ratingService.saveRating(createRatingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", "Rating Created Successfully"));
    }

    @GetMapping("/{quizId}")
    public QuizRatingDTO getRatingsByQuizId(@PathVariable UUID quizId) throws NotFoundException {
       return ratingService.getRatingByQuizId(quizId);
    }
}
