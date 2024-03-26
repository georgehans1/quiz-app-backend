package com.quizapp.quiz.dto;

import com.quizapp.category.models.Category;
import com.quizapp.quiz.enums.DifficultyLevel;
import lombok.Data;

import java.util.UUID;

@Data
public class QuizCreateRequest {
    private UUID categoryId;
    private String title;
    private String description;
    private String tag;
    private String quizImage;
    private DifficultyLevel difficultyLevel;
}
