package com.quizapp.auth.config;

import com.quizapp.auth.service.AuthorityService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AuthoritiesInitializer implements ApplicationRunner {

    private final AuthorityService authorityService;

    public AuthoritiesInitializer(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        authorityService.initAuthorities();
    }
}