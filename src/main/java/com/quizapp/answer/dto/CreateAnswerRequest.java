package com.quizapp.answer.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateAnswerRequest {
    private UUID questionId;
    private String text;
    private Boolean isCorrect;
}
