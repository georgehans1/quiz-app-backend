package com.quizapp.user.service;

import com.quizapp.auth.models.Authority;
import com.quizapp.user.dto.OidcUserInfo;
import com.quizapp.user.models.User;
import com.quizapp.auth.repository.AuthorityRepository;
import com.quizapp.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

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
            User createdUser = user.get();
            OidcUserInfo userInfo = OidcUserInfo.builder()
                    .id(createdUser.getUserId().toString())
                    .name(createdUser.getUserName())
                    .email(createdUser.getEmail())
                    .picture(createdUser.getUserImage())
                    .provider(createdUser.getProvider())
                    .role(createdUser.getAuthorities().stream().map(authority -> authority.getName().toString()).collect(Collectors.joining(", ")))
                    .build();
            return userInfo;
        } else {
            return null;
        }
    }

    @Transactional
    public void changeUserRole(UUID userId, UUID roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Authority role = authorityRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Add the new role to the user's authorities
        user.getAuthorities().add(role);

        // Save the updated user entity
        userRepository.save(user);
    }


}
