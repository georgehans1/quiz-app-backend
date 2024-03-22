package com.quizapp.take.service;

import com.quizapp.common.exceptions.NotFoundException;
import com.quizapp.take.dto.TakeCreateRequest;
import com.quizapp.take.dto.UserTakesDTO;
import com.quizapp.take.models.Take;

import java.util.List;
import java.util.UUID;

public interface ITakeService {
    Take takeQuiz(TakeCreateRequest request) throws NotFoundException;
    UserTakesDTO checkUserTake(UUID userId, UUID quizId);
    List<Take> getUserTakes(UUID userId);
    List<Take> getTakesByQuizId(UUID quizId);
}
