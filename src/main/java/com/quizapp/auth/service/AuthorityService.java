package com.quizapp.auth.service;

import com.quizapp.auth.models.Authority;
import com.quizapp.user.enums.Roles;
import com.quizapp.auth.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

    @Autowired
    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public void initAuthorities() {
        if (authorityRepository.count() == 0) {
            Authority userAuthority = new Authority();
            userAuthority.setName(Roles.USER);
            authorityRepository.save(userAuthority);

            Authority adminAuthority = new Authority();
            adminAuthority.setName(Roles.ADMIN);
            authorityRepository.save(adminAuthority);
        }
    }
}