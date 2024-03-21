package com.quizapp.take.repository;

import com.quizapp.take.models.Take;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TakeRepository extends JpaRepository<Take, UUID> {
    @Query("SELECT t FROM Take t WHERE t.user.userId = :userId AND t.quiz.quizId = :quizId")
    List<Take> findAllByUserIdAndQuizId(@Param("userId") UUID userId, @Param("quizId") UUID quizId);
}
