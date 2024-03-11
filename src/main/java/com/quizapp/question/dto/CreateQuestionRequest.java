package com.quizapp.question.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateQuestionRequest {
    private UUID quizId;
    private String text;
}
