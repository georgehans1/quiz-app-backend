package com.quizapp.quiz.dto;

import com.quizapp.category.dto.CategoryResponse;
import com.quizapp.category.models.Category;
import com.quizapp.question.models.Question;
import com.quizapp.quiz.models.Quiz;
import com.quizapp.rating.models.Rating;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResponse {
    private UUID quizId;
    private CategoryResponse category;
    private String title;
    private String description;
    private String tag;
    private String quizImage;
    private Timestamp createdAt;
    private Boolean isActive;


    public static QuizResponse fromQuiz(Quiz quiz)
    {
        CategoryResponse categoryResponse = CategoryResponse.fromCategory(quiz.getCategory());
        return new QuizResponse(
                quiz.getQuizId(),
                categoryResponse,
                quiz.getTitle(),
                quiz.getDescription(),
                quiz.getTag(),
                quiz.getQuizImage(),
                quiz.getCreatedAt(),
                quiz.getIsActive()
        );
    }

}
