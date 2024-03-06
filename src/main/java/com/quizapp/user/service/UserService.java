package com.quizapp.user.service;

import com.quizapp.user.dto.OidcUserInfo;
import com.quizapp.user.models.User;
import com.quizapp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public OidcUserInfo getCurrentUser(Authentication userDetails) {
        String email = ((DefaultOidcUser) userDetails.getPrincipal()).getEmail();
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            User user1 = user.get();
            OidcUserInfo userInfo = OidcUserInfo.builder()
                    .id(user1.getId().toString())
                    .name(user1.getUserName())
                    .email(user1.getEmail())
                    .picture(user1.getUserImage())
                    .provider(user1.getProviderId())
                    .role(user1.getUserRole())
                    .build();
            return userInfo;
        } else {
            return null;
        }
    }


}
