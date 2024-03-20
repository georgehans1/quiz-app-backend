package com.quizapp.result.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.quizapp.common.exceptions.NotFoundException;
import com.quizapp.quiz.models.Quiz;
import com.quizapp.quiz.service.QuizService;
import com.quizapp.result.dto.LeaderboardDTO;
import com.quizapp.result.dto.QuestionResultDTO;
import com.quizapp.result.dto.ResultDTO;
import com.quizapp.result.dto.ResultLeaderboardDTO;
import com.quizapp.result.models.Result;
import com.quizapp.result.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResultService implements IResultService{

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private QuizService quizService;

    @Override
    public ResultDTO getResultsByTakeId(UUID takeId) throws JsonProcessingException {
        ResultDTO resultDTO = ResultDTO.fromResult(resultRepository.findByTakeId(takeId));
        return resultDTO;
    }

    @Override
    public ResultLeaderboardDTO getQuizLeaderboard(UUID quizId) throws NotFoundException {

        ResultLeaderboardDTO leaderboardDTO = new ResultLeaderboardDTO();
        Quiz quiz = quizService.getQuizById(quizId);
        leaderboardDTO.setQuizId(quiz.getQuizId());

        List<Result> results = resultRepository.findByQuizId(quizId);

        Map<UUID, Result> bestResults = new HashMap<>();
        Map<UUID, Integer> numberOfTakes = new HashMap<>();
        for (Result result : results) {
            UUID userId = result.getUser().getUserId();
            if (!bestResults.containsKey(userId)) {
                bestResults.put(userId, result);
            } else {
                Result existingResult = bestResults.get(userId);
                if (result.getScore() > existingResult.getScore() ||
                        (result.getScore().equals(existingResult.getScore()) &&
                                result.getTake().getTimeElapsed() < existingResult.getTake().getTimeElapsed())) {
                    bestResults.put(userId, result);
                }
            }
            numberOfTakes.put(userId, numberOfTakes.getOrDefault(userId, 0) + 1);
        }

        List<LeaderboardDTO> leaderboard = new ArrayList<>();
        for (Result result : bestResults.values()) {
            LeaderboardDTO dto = LeaderboardDTO.fromResult(result);
            dto.setNumberOfTakes(numberOfTakes.getOrDefault(result.getUser().getUserId(), 0));
            leaderboard.add(dto);
        }

        // Sort the leaderboard by best score in descending order
        // and then by lowest time taken in ascending order
        leaderboard.sort(Comparator
                .comparing(LeaderboardDTO::getBestScore)
                .reversed()
                .thenComparing(LeaderboardDTO::getBestTimeTaken));

        leaderboardDTO.setTitle(quiz.getTitle());
        leaderboardDTO.setQuizImage(quiz.getQuizImage());
        leaderboardDTO.setQuizDifficulty(quiz.getDifficultyLevel());
        leaderboardDTO.setLeaderboardList(leaderboard);
        return leaderboardDTO;
    }
}
