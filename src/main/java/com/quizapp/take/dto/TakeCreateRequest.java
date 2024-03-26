package com.quizapp.take.dto;

import com.quizapp.answer.dto.AnswerSubmission;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TakeCreateRequest {
    private UUID quizId;
    private UUID userId;
    private Long timeElapsed;
    private List<AnswerSubmission> answers;
}
