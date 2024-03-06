package com.quizapp.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OidcUserInfo {
    private String id;
    private String name;
    private String picture;
    private String email;
    private String provider;
    private String role;
}

