package com.quizapp.take.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTakesDTO {
    private UUID userId;
    private UUID quizId;
    private Boolean isTaken;
    private int takeNumber;
}
