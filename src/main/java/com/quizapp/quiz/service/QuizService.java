package com.quizapp.quiz.service;

import com.quizapp.answer.dto.AnswerSubmission;
import com.quizapp.answer.models.Answer;
import com.quizapp.answer.service.IAnswerService;
import com.quizapp.category.service.ICategoryService;
import com.quizapp.common.exceptions.NotFoundException;
import com.quizapp.question.models.Question;
import com.quizapp.question.service.IQuestionService;
import com.quizapp.quiz.dto.QuizCreateRequest;
import com.quizapp.quiz.dto.QuizResponse;
import com.quizapp.quiz.dto.UserQuizResponse;
import com.quizapp.quiz.models.Quiz;
import com.quizapp.quiz.repository.QuizRepository;
import com.quizapp.result.models.Result;
import com.quizapp.result.repository.ResultRepository;
import com.quizapp.take.dto.TakeCreateRequest;
import com.quizapp.take.dto.TakeResponse;
import com.quizapp.take.models.Take;
import com.quizapp.take.repository.TakeRepository;
import com.quizapp.take.service.TakeService;
import com.quizapp.user.models.User;
import com.quizapp.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuizService implements IQuizService{

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private TakeRepository takeRepository;

    @Autowired
    private ICategoryService categoryService;

    private final TakeService takeService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IAnswerService answerService;

    @Autowired
    public QuizService(@Lazy TakeService takeService) {
        this.takeService = takeService;
    }

    public List<QuizResponse> getAllQuiz() {
        List<Quiz> allQuiz = quizRepository.findAll();
        log.info("Get All Quiz");
        return allQuiz.stream()
                .map(QuizResponse::fromQuiz)
                .collect(Collectors.toList());
    }

    @Override
    public QuizResponse getQuizResponseById(UUID id) throws NotFoundException {
        return quizRepository.findById(id).map(QuizResponse :: fromQuiz)
                .orElseThrow(() -> new NotFoundException("Quiz not found."));
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
                .difficultyLevel(quiz.getDifficultyLevel())
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

    public UserQuizResponse userQuizObject(UUID quizId) throws NotFoundException {
        UserQuizResponse quiz = UserQuizResponse.fromUserQuizResponse(getQuizById(quizId));
        Collections.shuffle(quiz.getQuestionList());
        quiz.getQuestionList().stream().forEach(questionResponse -> Collections.shuffle(questionResponse.getAnswerList()));
        return quiz;
    }

    public TakeResponse processQuizSubmission(TakeCreateRequest request) throws NotFoundException {
        Take take = takeService.takeQuiz(request);
        User currentUser = userService.getUserById(request.getUserId());
        Quiz currentQuiz = getQuizById(request.getQuizId());

        Result result = new Result();
        result.setQuiz(currentQuiz);
        result.setUser(currentUser);
//        List<Answer> correctAnswers = answerService.getCorrectAnswersForQuiz(request.getQuizId());
//        List<AnswerResponse> wrongAnswers = new ArrayList<>();
        int score = 0;
        for (AnswerSubmission answerDTO : request.getAnswers()) {
            Question question = questionService.findQuestionById(answerDTO.getQuestionId());
                for(Answer answer : question.getAnswers()){
                    if(answer.getAnswerId().equals(answerDTO.getSelectedAnswerId()) && answer.getIsCorrect().equals(true)){
                        score++;
                    }
                }
        }
        result.setScore(score);
        result.setTake(take);
        take.setResult(result);
        resultRepository.save(result);

        result = resultRepository.findByTakeId(take.getTakeId());
        take.setResult(result);
        takeRepository.save(take);
        return TakeResponse.fromTakeResponse(take);
    }


    @Override
    public void editQuiz(UUID id, QuizCreateRequest createRequest) throws NotFoundException {

    }

    @Override
    public void deleteQuiz(UUID id) throws NotFoundException {

    }
}
