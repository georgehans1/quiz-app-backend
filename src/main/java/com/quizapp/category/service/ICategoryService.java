package com.quizapp.category.service;

import com.quizapp.category.dto.CategoryCreateRequest;
import com.quizapp.category.dto.CategoryResponse;
import com.quizapp.category.models.Category;
import com.quizapp.common.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ICategoryService {
    List<CategoryResponse> getAllCategories();
    CategoryResponse getTransformCategoryById(UUID id) throws NotFoundException;

    Category getCategoryById(UUID id) throws NotFoundException;

    Category saveCategory(CategoryCreateRequest category);
    void editCategory(UUID id, CategoryCreateRequest createRequest) throws NotFoundException;
    void deleteCategory(UUID id) throws NotFoundException;
}
