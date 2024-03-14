package com.quizapp.take.dto;

import com.quizapp.take.models.Take;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TakeResponse {

    private UUID takeId;
    private UUID userId;
    private UUID quizId;
    private Long timeElapsed;
    private int result;


    public static TakeResponse fromTakeResponse(Take take){
        return new TakeResponse(
                take.getTakeId(),
                take.getUser().getUserId(),
                take.getQuiz().getQuizId(),
                take.getTimeElapsed(),
                take.getResult().getScore()
        );
    }
}
