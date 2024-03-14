package com.quizapp.answer.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AnswerSubmission {
    private UUID questionId;
    private UUID selectedAnswerId;

}
