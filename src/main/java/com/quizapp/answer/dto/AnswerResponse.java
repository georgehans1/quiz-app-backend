package com.quizapp.answer.dto;

import com.quizapp.answer.models.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResponse {
    private UUID answerId;
    private UUID questionId;
    private String text;
    private Timestamp createdAt;
    private Boolean isCorrect;

    public static AnswerResponse fromAnswer(Answer answer) {
        return new AnswerResponse(
                answer.getAnswerId(),
                answer.getQuestion().getQuestionId(),
                answer.getText(),
                answer.getCreatedAt(),
                answer.getIsCorrect()
        );
    }
}
