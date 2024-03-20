package com.quizapp.take.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quizapp.answer.dto.AnswerSubmission;
import com.quizapp.quiz.models.Quiz;
import com.quizapp.result.dto.QuestionResultDTO;
import com.quizapp.result.models.Result;
import com.quizapp.user.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "quizapp",name="take")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Take {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID takeId;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    @JsonIgnore
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Long timeElapsed;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp dateTaken;

    @OneToOne(mappedBy = "take")
    private Result result;
}