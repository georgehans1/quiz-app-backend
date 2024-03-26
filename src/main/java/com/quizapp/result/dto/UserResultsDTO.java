package com.quizapp.result.dto;

import com.quizapp.result.models.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResultsDTO {
    private UUID resultId;
    private UUID userId;
    private UUID quizId;
    private String quizImage;
    private String quizTitle;
    private Integer score;
    private Integer questionCount;
    private Long timeElapsed;
    private Timestamp createdAt;

    public static UserResultsDTO fromUserResults(Result result){
        return new UserResultsDTO(
                result.getResultId(),
                result.getUser().getUserId(),
                result.getQuiz().getQuizId(),
                result.getQuiz().getQuizImage(),
                result.getQuiz().getTitle(),
                result.getScore(),
                result.getQuestionCount(),
                result.getTake().getTimeElapsed(),
                result.getCreatedAt()
        );
    }
}
