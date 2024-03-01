package com.quizapp.service;

import com.quizapp.dto.OidcUserInfo;
import com.quizapp.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface IUserService {
    User getUserByEmail(String email);
    List<User> getAllUsers();
    OidcUserInfo getCurrentUser(Authentication userDetails);
}
