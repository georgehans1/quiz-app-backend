package com.quizapp.take.service;

import com.quizapp.common.exceptions.NotFoundException;
import com.quizapp.quiz.service.QuizService;
import com.quizapp.take.dto.TakeCreateRequest;
import com.quizapp.take.dto.UserTakesDTO;
import com.quizapp.take.models.Take;
import com.quizapp.take.repository.TakeRepository;
import com.quizapp.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    @Override
    public UserTakesDTO checkUserTake(UUID userId, UUID quizId) {
        UserTakesDTO userTake = new UserTakesDTO();
        List<Take> takes = takeRepository.findAllByUserIdAndQuizId(userId,quizId);
        userTake.setUserId(userId);
        userTake.setQuizId(quizId);
        userTake.setIsTaken(!takes.isEmpty());
        userTake.setTakeNumber(takes.size());
        return userTake;
    }

    @Override
    public List<Take> getUserTakes(UUID userId) {
        List<Take> takes = takeRepository.findAllByUserId(userId);
        return takes;
    }

    @Override
    public List<Take> getTakesByQuizId(UUID quizId) {
        List<Take> takes = takeRepository.findAllByQuizId(quizId);
        return takes;
    }
}
