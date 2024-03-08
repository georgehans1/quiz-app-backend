package com.quizapp.category.controller;

import com.quizapp.category.dto.CategoryCreateRequest;
import com.quizapp.category.dto.CategoryResponse;
import com.quizapp.category.models.Category;
import com.quizapp.category.service.CategoryService;
import com.quizapp.category.service.ICategoryService;
import com.quizapp.common.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CategoryResponse>> getCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity saveCategory(@RequestBody CategoryCreateRequest category){
        categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", "Category created successfully"));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(categoryService.getTransformCategoryById(id));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void editCategory(@PathVariable UUID id, @RequestBody CategoryCreateRequest category) throws NotFoundException {
        log.info("Category {}", category);
        categoryService.editCategory(id, category);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@PathVariable UUID id) throws NotFoundException {
        log.info("Category {}", id);
        categoryService.deleteCategory(id);
    }


}
