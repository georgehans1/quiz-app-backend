package com.quizapp.result.models;

import com.quizapp.quiz.models.Quiz;
import com.quizapp.user.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "quizapp",name="result")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID resultId;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false, name = "date_taken")
    private LocalDateTime dateTaken;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

}