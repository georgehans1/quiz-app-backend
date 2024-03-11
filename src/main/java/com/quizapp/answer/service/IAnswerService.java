package com.quizapp.answer.service;

import com.quizapp.answer.dto.AnswerResponse;
import com.quizapp.answer.dto.CreateAnswerRequest;
import com.quizapp.answer.models.Answer;
import com.quizapp.common.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface IAnswerService {
    Answer saveAnswer(CreateAnswerRequest answerRequest) throws NotFoundException;
    List<AnswerResponse> getAllAnswersByQuestionId(UUID questionId);
}
