package com.quizapp.answer.repository;

import com.quizapp.answer.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,UUID> {
    @Query("SELECT a FROM Answer a WHERE a.question.id = :questionId")
    List<Answer> findAllByQuestionId(UUID questionId);
}
