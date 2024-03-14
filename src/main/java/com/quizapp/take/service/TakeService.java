package com.quizapp.take.service;

import com.quizapp.common.exceptions.NotFoundException;
import com.quizapp.quiz.service.QuizService;
import com.quizapp.take.dto.TakeCreateRequest;
import com.quizapp.take.models.Take;
import com.quizapp.take.repository.TakeRepository;
import com.quizapp.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TakeService implements ITakeService {

    @Autowired
    private TakeRepository takeRepository;

    private final QuizService quizService;

    @Autowired
    private UserService userService;

    @Autowired
    public TakeService(@Lazy QuizService quizService) {
        this.quizService = quizService;
    }


    public Take takeQuiz(TakeCreateRequest request) throws NotFoundException {
        Take take = Take.builder()
                .quiz(quizService.getQuizById(request.getQuizId()))
                .user(userService.getUserById(request.getUserId()))
                .timeElapsed(request.getTimeElapsed()).build();
        return takeRepository.save(take);
    }
}
