package com.quizapp.category.service;

import com.quizapp.category.dto.CategoryCreateRequest;
import com.quizapp.category.dto.CategoryResponse;
import com.quizapp.category.models.Category;
import com.quizapp.category.repository.CategoryRepository;
import com.quizapp.common.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryResponse> getAllCategories() {
        List<Category> allCategory = categoryRepository.findAll();
        log.info("Get All Categories");
        return allCategory.stream()
                .map(CategoryResponse::fromCategory)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getTransformCategoryById(UUID id) throws NotFoundException {
        return categoryRepository.findById(id).map(CategoryResponse :: fromCategory)
                .orElseThrow(() -> new NotFoundException("Category not found."));
    }

    @Override
    public Category getCategoryById(UUID id) throws NotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found."));
    }

    @Override
    public Category saveCategory(CategoryCreateRequest category) {
        Category saveCategory = Category.builder().categoryName(category.getCategoryName())
                .categoryImage(category.getCategoryImage())
                .description(category.getDescription())
                .build();
        log.info("Build {}", saveCategory);
        categoryRepository.save(saveCategory);
        return saveCategory;
    }

    @Override
    public void editCategory(UUID id, CategoryCreateRequest createRequest) throws NotFoundException {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category Not Found"));
        if(Objects.nonNull(createRequest.getDescription())) category.setDescription(createRequest.getDescription());
        if(Objects.nonNull(createRequest.getCategoryName())) category.setCategoryName(createRequest.getCategoryName());
        if(Objects.nonNull(createRequest.getCategoryImage())) category.setCategoryImage(createRequest.getCategoryImage());
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(UUID id) throws NotFoundException {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Category Not Found"));
          categoryRepository.delete(category);
    }
}
