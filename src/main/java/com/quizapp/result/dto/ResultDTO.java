package com.quizapp.result.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizapp.result.models.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {
    private UUID resultId;
    private UUID userId;
    private String userName;
    private String userImage;
    private UUID quizId;
    private Integer score;
    private Integer questionCount;
    private List<QuestionResultDTO> userSubmission;
    private Timestamp createdAt;

    public static ResultDTO fromResult(Result result) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = result.getUserSubmission();
         List<QuestionResultDTO> resultDTO = objectMapper.readValue(json, new TypeReference<List<QuestionResultDTO>>(){});
        return new ResultDTO(
                result.getResultId(),
                result.getUser().getUserId(),
                result.getUser().getUserImage(),
                result.getUser().getUserName(),
                result.getQuiz().getQuizId(),
                result.getScore(),
                result.getQuestionCount(),
                resultDTO,
                result.getCreatedAt()
        );
    }
}
