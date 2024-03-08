package com.quizapp.auth.service;

import com.quizapp.auth.models.Authority;
import com.quizapp.user.dto.OidcUserInfo;
import com.quizapp.user.enums.Roles;
import com.quizapp.user.models.User;
import com.quizapp.auth.repository.AuthorityRepository;
import com.quizapp.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OAuth2UserService extends OidcUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    @SneakyThrows
    public OidcUser loadUser(OidcUserRequest oidcUserRequest) throws OAuth2AuthenticationException {
        log.trace("Load user {}", oidcUserRequest);
        OidcUser oidcUser = super.loadUser(oidcUserRequest);
        return processOidcUser(oidcUserRequest, oidcUser);
    }

    private OidcUser processOidcUser(OidcUserRequest oidcUserRequest, OidcUser oidcUser) {
        OidcUserInfo userInfoDto = OidcUserInfo
                .builder()
                .name(oidcUser.getAttributes().get("name").toString())
                .id(oidcUser.getAttributes().get("sub").toString())
                .email(oidcUser.getAttributes().get("email").toString())
                .picture(oidcUser.getAttributes().get("picture").toString())
                .provider(oidcUserRequest.getClientRegistration().getRegistrationId())
                .build();

        log.info("User info is {}", userInfoDto);
        String userEmail = userInfoDto.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        User user = userOptional
                .map(existingUser -> updateExistingUser(existingUser, userInfoDto))
                .orElseGet(() -> registerNewUser(userInfoDto));
        log.info("User Token is {}", oidcUser.getIdToken().getTokenValue());
        return new DefaultOidcUser(oidcUser.getAuthorities(), oidcUserRequest.getIdToken());
    }

    private User registerNewUser(OidcUserInfo userInfoDto) {
        User user = User.builder()
                .provider(userInfoDto.getProvider())
                .userName(userInfoDto.getName())
                .email(userInfoDto.getEmail())
                .userImage(userInfoDto.getPicture())
                .build();
        Authority authority = authorityRepository.findByName(Roles.USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Assign the authority to the user
        user.setAuthorities(Collections.singleton(authority));
        log.info("User Info is {}", user);
        userRepository.save(user);
        return user;
    }

    private User updateExistingUser(User existingUser,OidcUserInfo userInfoDto) {
        log.info("{}","Authenticated");
        existingUser.setUserName(userInfoDto.getName());
        existingUser.setUserImage(userInfoDto.getPicture());
        return userRepository.save(existingUser);
    }
}