package com.quizapp.question.service;

import com.quizapp.common.exceptions.NotFoundException;
import com.quizapp.question.dto.CreateQuestionRequest;
import com.quizapp.question.dto.QuestionResponse;
import com.quizapp.question.models.Question;
import com.quizapp.question.repository.QuestionRepository;
import com.quizapp.quiz.dto.QuizCreateRequest;
import com.quizapp.quiz.models.Quiz;
import com.quizapp.quiz.service.QuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuestionService implements IQuestionService {

    @Autowired
    private QuestionRepository questionRepository;


    private final QuizService quizService;


    @Autowired
    public QuestionService(@Lazy QuizService quizService) {
        this.quizService = quizService;
    }

    @Override
    public Question saveQuestion(CreateQuestionRequest questionRequest) throws NotFoundException {
        Question question = Question.builder()
                .text(questionRequest.getText())
                .quiz(quizService.getQuizById(questionRequest.getQuizId()))
                        .build();
        log.info("Creating Question");
        questionRepository.save(question);
        return question;
    }

    @Override
    public Question findQuestionById(UUID questionId) throws NotFoundException{
        return questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException("Question Not Found"));
    }

    @Override
    public List<QuestionResponse> getAllQuestionsByQuizId(UUID quizId){
        List<Question> questionList = questionRepository.findAllByQuizId(quizId);
        return questionList.stream().map(QuestionResponse :: fromQuestion).collect(Collectors.toList());
    }
}
