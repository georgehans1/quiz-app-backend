package com.quizapp.quiz.models;

import com.quizapp.category.models.Category;
import com.quizapp.question.models.Question;
import com.quizapp.quiz.enums.DifficultyLevel;
import com.quizapp.rating.models.Rating;
import com.quizapp.take.models.Take;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "quizapp",name="quiz")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID quizId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private String title;

    @Column(name = "description",nullable = false, length = 2048)
    private String description;

    @Column(nullable = false)
    private String tag;

    @Column(nullable = false)
    private String quizImage;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    private DifficultyLevel difficultyLevel;

    @OneToMany(mappedBy = "quiz")
    private List<Question> questions;

    @OneToMany(mappedBy = "quiz")
    private List<Rating> ratings;

    @OneToMany(mappedBy = "quiz")
    private List<Take> takes;
}