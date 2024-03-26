package com.quizapp.rating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizRatingDTO {
    private UUID quizId;
    private int averageRating;
    private int ratingCount;
    private Map<Integer, Integer> ratingNumbers;
    private List<RatingDTO> ratingDTOList;
}
