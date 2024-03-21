package com.quizapp.rating.repository;

import com.quizapp.rating.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<Rating,UUID> {
    @Query("SELECT r FROM Rating r WHERE r.quiz.id = :quizId")
    List<Rating> findAllByQuizId(@Param("quizId") UUID quizId);
}
