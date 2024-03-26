package com.quizapp.result.dto;

import com.quizapp.quiz.enums.DifficultyLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultLeaderboardDTO {
    private UUID quizId;
    private String title;
    private String quizImage;
    private DifficultyLevel quizDifficulty;
    private List<LeaderboardDTO> leaderboardList;
}
