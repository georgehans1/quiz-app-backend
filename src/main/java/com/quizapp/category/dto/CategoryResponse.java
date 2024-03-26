package com.quizapp.category.dto;

import com.quizapp.category.models.Category;
import com.quizapp.quiz.models.Quiz;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private UUID categoryId;
    private String categoryName;
    private String description;
    private String categoryImage;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public static CategoryResponse fromCategory(Category category)
    {
        return new CategoryResponse(
                category.getCategoryId(),
                category.getCategoryName(),
                category.getDescription(),
                category.getCategoryImage(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
