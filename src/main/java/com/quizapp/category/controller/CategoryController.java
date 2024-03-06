package com.quizapp.category.controller;

import com.quizapp.category.dto.CategoryCreateRequest;
import com.quizapp.category.models.Category;
import com.quizapp.category.service.CategoryService;
import com.quizapp.category.service.ICategoryService;
import com.quizapp.common.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Category>> getCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCategory(@RequestBody CategoryCreateRequest category){
         log.info("Category {}", category);
        categoryService.saveCategory(category);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Category> getCategoryById(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
}
