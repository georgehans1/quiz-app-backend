package com.quizapp.rating.service;

import com.quizapp.common.exceptions.NotFoundException;
import com.quizapp.rating.dto.CreateRatingRequest;
import com.quizapp.rating.dto.QuizRatingDTO;
import com.quizapp.rating.dto.RatingDTO;
import com.quizapp.rating.models.Rating;

import java.util.List;
import java.util.UUID;

public interface IRatingService {
    Rating saveRating(CreateRatingRequest createRatingRequest) throws NotFoundException;

    QuizRatingDTO getRatingByQuizId(UUID quizId) throws NotFoundException;
}
