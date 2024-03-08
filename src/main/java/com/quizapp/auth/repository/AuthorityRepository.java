package com.quizapp.auth.repository;

import com.quizapp.auth.models.Authority;
import com.quizapp.user.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, UUID> {
    Optional<Authority> findByName(Roles name);
}
