package com.quizapp.category.dto;

import lombok.Data;

@Data
public class CategoryCreateRequest {
    private String categoryName;
    private String description;
    private String categoryImage;
}
