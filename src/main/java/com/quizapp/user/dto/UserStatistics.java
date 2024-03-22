package com.quizapp.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatistics {
    private UUID userId;
    private String userName;
    private String userImage;
    private List<Map<String, Object>> userStats;
}
