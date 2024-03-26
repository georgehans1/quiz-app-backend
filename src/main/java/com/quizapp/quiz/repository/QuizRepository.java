package com.quizapp.quiz.repository;

import com.quizapp.quiz.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID> {
    @Query("SELECT q FROM Quiz q JOIN q.category c WHERE c.categoryId = :categoryId")
    List<Quiz> findAllByCategoryId(UUID categoryId);
}
