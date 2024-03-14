package com.quizapp.take.service;

import com.quizapp.common.exceptions.NotFoundException;
import com.quizapp.take.dto.TakeCreateRequest;
import com.quizapp.take.models.Take;

public interface ITakeService {
    Take takeQuiz(TakeCreateRequest request) throws NotFoundException;
}
