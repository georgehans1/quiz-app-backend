package com.quizapp.quiz.service;

import com.quizapp.category.service.CategoryService;
import com.quizapp.category.service.ICategoryService;
import com.quizapp.common.exceptions.NotFoundException;
import com.quizapp.quiz.dto.QuizCreateRequest;
import com.quizapp.quiz.dto.QuizResponse;
import com.quizapp.quiz.models.Quiz;
import com.quizapp.quiz.repository.QuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuizService implements IQuizService{

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private ICategoryService categoryService;


    public List<QuizResponse> getAllQuiz() {
        List<Quiz> allQuiz = quizRepository.findAll();
        log.info("Get All Quiz");
        return allQuiz.stream()
                .map(QuizResponse::fromQuiz)
                .collect(Collectors.toList());
    }

    @Override
    public Quiz getQuizById(UUID id) throws NotFoundException {
        return quizRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quiz not found."));
    }

    @Override
    public List<QuizResponse> getQuizByCategoryId(UUID categoryId) {
        List<Quiz> allQuiz = quizRepository.findAllByCategoryId(categoryId);
        quizRepository.findAll();
        log.info("Get All Quiz By Category");
        return allQuiz.stream()
                .map(QuizResponse::fromQuiz)
                .collect(Collectors.toList());
    }

    @Override
    public Quiz saveQuiz(QuizCreateRequest quiz) throws NotFoundException {
        Quiz saveQuiz = Quiz.builder()
                .title(quiz.getTitle())
                .quizImage(quiz.getQuizImage())
                .description(quiz.getDescription())
                .category(categoryService.getCategoryById(quiz.getCategoryId()))
                .tag(quiz.getTag())
                .isActive(false)
                .build();
        log.info("Creating Quiz");
        quizRepository.save(saveQuiz);
        return saveQuiz;
    }

    @Override
    public void changeQuizActiveStatus(UUID id) throws NotFoundException{
        Quiz quiz = getQuizById(id);
        quiz.setIsActive(!quiz.getIsActive());
        quizRepository.save(quiz);
    }

    @Override
    public void editQuiz(UUID id, QuizCreateRequest createRequest) throws NotFoundException {

    }

    @Override
    public void deleteQuiz(UUID id) throws NotFoundException {

    }
}
