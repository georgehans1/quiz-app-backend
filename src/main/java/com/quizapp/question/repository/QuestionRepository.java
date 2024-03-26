package com.quizapp.question.repository;

import com.quizapp.question.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    @Query("SELECT q FROM Question q WHERE q.quiz.id = :quizId")
    List<Question> findAllByQuizId(UUID quizId);
}
