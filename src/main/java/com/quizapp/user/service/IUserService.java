package com.quizapp.user.service;

import com.quizapp.user.dto.OidcUserInfo;
import com.quizapp.user.models.User;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    User getUserByEmail(String email);
    User getUserById(UUID id);
    List<User> getAllUsers();
    OidcUserInfo getCurrentUser(Authentication userDetails);
}
