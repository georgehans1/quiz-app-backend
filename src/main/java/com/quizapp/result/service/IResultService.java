package com.quizapp.result.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.quizapp.common.exceptions.NotFoundException;
import com.quizapp.result.dto.QuestionResultDTO;
import com.quizapp.result.dto.ResultDTO;
import com.quizapp.result.dto.ResultLeaderboardDTO;
import com.quizapp.result.models.Result;

import java.util.List;
import java.util.UUID;

public interface IResultService {
    ResultDTO getResultsByTakeId(UUID takeId) throws JsonProcessingException;
    ResultLeaderboardDTO getQuizLeaderboard(UUID quizId) throws NotFoundException;
}
