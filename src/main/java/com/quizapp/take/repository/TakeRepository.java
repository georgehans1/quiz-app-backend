package com.quizapp.take.repository;

import com.quizapp.take.models.Take;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TakeRepository extends JpaRepository<Take, UUID> {
}
