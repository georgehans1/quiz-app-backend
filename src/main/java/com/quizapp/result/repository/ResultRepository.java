package com.quizapp.result.repository;

import com.quizapp.result.models.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResultRepository extends JpaRepository<Result, UUID> {
    @Query("SELECT r FROM Result r WHERE r.take.id = :takeId")
    Result findByTakeId(@Param("takeId") UUID takeId);

    @Query("SELECT r FROM Result r WHERE r.quiz.quizId = :quizId")
    List<Result> findByQuizId(UUID quizId);

    @Query("SELECT r FROM Result r WHERE r.user.userId = :userId")
    List<Result> findAllByUserId(@Param("userId") UUID userId);
}
