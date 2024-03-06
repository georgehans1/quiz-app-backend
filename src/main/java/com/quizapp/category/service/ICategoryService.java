package com.quizapp.category.service;

import com.quizapp.category.dto.CategoryCreateRequest;
import com.quizapp.category.models.Category;
import com.quizapp.common.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface ICategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(UUID id) throws NotFoundException;
    void saveCategory(CategoryCreateRequest category);
}
