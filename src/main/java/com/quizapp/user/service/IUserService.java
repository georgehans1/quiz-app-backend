package com.quizapp.user.service;

import com.quizapp.user.dto.OidcUserInfo;
import com.quizapp.user.models.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IUserService {
    User getUserByEmail(String email);
    List<User> getAllUsers();
    OidcUserInfo getCurrentUser(Authentication userDetails);
}
