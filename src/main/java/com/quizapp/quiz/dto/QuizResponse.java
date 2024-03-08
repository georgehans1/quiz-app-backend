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
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResponse {
    private UUID quizId;
    private UUID categoryId;
    private String title;
    private String description;
    private String tag;
    private String quizImage;
    private Timestamp createdAt;


    public static QuizResponse fromQuiz(Quiz quiz)
    {
        return new QuizResponse(
                quiz.getQuizId(),
                quiz.getCategory().getCategoryId(),
                quiz.getTitle(),
                quiz.getDescription(),
                quiz.getTag(),
                quiz.getQuizImage(),
                quiz.getCreatedAt()
        );
    }

}
