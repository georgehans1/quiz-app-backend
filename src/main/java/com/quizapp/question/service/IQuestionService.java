package com.quizapp.question.service;

import com.quizapp.common.exceptions.NotFoundException;
import com.quizapp.question.dto.CreateQuestionRequest;
import com.quizapp.question.dto.QuestionResponse;
import com.quizapp.question.models.Question;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

public interface IQuestionService {
     Question saveQuestion(CreateQuestionRequest questionRequest) throws NotFoundException;
     List<QuestionResponse> getAllQuestionsByQuizId(UUID quizId);
     Question findQuestionById(UUID questionId) throws NotFoundException;
}
