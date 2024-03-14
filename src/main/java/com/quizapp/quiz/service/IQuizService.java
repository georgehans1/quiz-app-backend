package com.quizapp.quiz.service;

import com.quizapp.common.exceptions.NotFoundException;
import com.quizapp.quiz.dto.QuizCreateRequest;
import com.quizapp.quiz.dto.QuizResponse;
import com.quizapp.quiz.dto.UserQuizResponse;
import com.quizapp.quiz.models.Quiz;
import com.quizapp.take.dto.TakeCreateRequest;
import com.quizapp.take.dto.TakeResponse;

import java.util.List;
import java.util.UUID;

public interface IQuizService {
    List<QuizResponse> getAllQuiz();
    Quiz getQuizById(UUID id) throws NotFoundException;
    QuizResponse getQuizResponseById(UUID id) throws NotFoundException;
    Quiz saveQuiz(QuizCreateRequest quiz) throws NotFoundException;
    void editQuiz(UUID id, QuizCreateRequest quiz) throws NotFoundException;
    void deleteQuiz(UUID id) throws NotFoundException;
    List<QuizResponse> getQuizByCategoryId(UUID id);
    void changeQuizActiveStatus(UUID id)throws NotFoundException;
    TakeResponse processQuizSubmission(TakeCreateRequest request) throws NotFoundException;
    UserQuizResponse userQuizObject(UUID quizId) throws NotFoundException;
}
