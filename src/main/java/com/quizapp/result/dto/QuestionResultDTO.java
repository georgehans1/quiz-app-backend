package com.quizapp.result.dto;

import com.quizapp.answer.models.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResultDTO {
    private UUID quizId;
    private UUID takeId;
    private String text;
    private String userAnswer;
    private String correctAnswer;
    private Boolean isCorrect;
}
