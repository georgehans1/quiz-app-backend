package com.quizapp.result.dto;

import com.quizapp.result.models.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaderboardDTO {
    private UUID userId;
    private String userImage;
    private String userName;
    private Integer bestScore;
    private Long bestTimeTaken;
    private Integer numberOfTakes;
    private Timestamp dateAchieved;

    public static LeaderboardDTO fromResult(Result result) {
        LeaderboardDTO dto = new LeaderboardDTO();
        dto.setUserId(result.getUser().getUserId());
        dto.setUserName(result.getUser().getUserName());
        dto.setUserImage(result.getUser().getUserImage());
        dto.setBestScore(result.getScore());
        dto.setBestTimeTaken(result.getTake().getTimeElapsed());
        dto.setDateAchieved(result.getCreatedAt());
        return dto;
    }
}
