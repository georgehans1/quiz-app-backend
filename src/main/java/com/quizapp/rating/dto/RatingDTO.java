package com.quizapp.rating.dto;

import com.quizapp.rating.models.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {

    private UUID ratingId;
    private UUID userId;
    private String userImage;
    private String userName;
    private int rating;
    private String text;
    private String description;
    private Timestamp createdAt;


    public static RatingDTO fromRating(Rating rating){
        return new RatingDTO(
                rating.getRatingId(),
                rating.getUser().getUserId(),
                rating.getUser().getUserImage(),
                rating.getUser().getUserName(),
                rating.getRating(),
                rating.getText(),
                rating.getDescription(),
                rating.getCreatedAt()
        );
    }
}
