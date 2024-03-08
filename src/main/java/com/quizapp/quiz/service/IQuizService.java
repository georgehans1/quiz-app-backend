package com.quizapp.quiz.service;

import com.quizapp.category.dto.CategoryCreateRequest;
import com.quizapp.category.dto.CategoryResponse;
import com.quizapp.category.models.Category;
import com.quizapp.common.exceptions.NotFoundException;
import com.quizapp.quiz.dto.QuizCreateRequest;
import com.quizapp.quiz.dto.QuizResponse;
import com.quizapp.quiz.models.Quiz;

import java.util.List;
import java.util.UUID;

public interface IQuizService {
    List<QuizResponse> getAllQuiz();
    Quiz getQuizById(UUID id) throws NotFoundException;
    Quiz saveQuiz(QuizCreateRequest quiz) throws NotFoundException;
    void editQuiz(UUID id, QuizCreateRequest quiz) throws NotFoundException;
    void deleteQuiz(UUID id) throws NotFoundException;
    List<QuizResponse> getQuizByCategoryId(UUID id);
}
