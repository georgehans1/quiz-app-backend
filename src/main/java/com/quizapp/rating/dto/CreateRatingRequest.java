package com.quizapp.rating.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CreateRatingRequest {
    private UUID quizId;
    private UUID userId;
    private int rating;
    private String text;
    private String description;
}
