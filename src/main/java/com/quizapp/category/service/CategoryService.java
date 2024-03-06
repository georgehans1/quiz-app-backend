package com.quizapp.category.service;

import com.quizapp.category.dto.CategoryCreateRequest;
import com.quizapp.category.models.Category;
import com.quizapp.category.repository.CategoryRepository;
import com.quizapp.common.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(UUID id) throws NotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found."));
    }

    public void saveCategory(CategoryCreateRequest category) {
        Category saveCategory = Category.builder().categoryName(category.getCategoryName())
                .categoryImage(category.getCategoryImage())
                .description(category.getDescription())
                .build();
        log.info("Build {}", saveCategory);
        categoryRepository.save(saveCategory);
    }
}
