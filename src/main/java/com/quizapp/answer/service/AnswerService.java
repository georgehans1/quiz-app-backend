package com.quizapp.answer.service;

import com.quizapp.answer.dto.AnswerResponse;
import com.quizapp.answer.dto.CreateAnswerRequest;
import com.quizapp.answer.models.Answer;
import com.quizapp.answer.repository.AnswerRepository;
import com.quizapp.common.exceptions.NotFoundException;
import com.quizapp.question.dto.QuestionResponse;
import com.quizapp.question.models.Question;
import com.quizapp.question.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@Service
public class AnswerService implements IAnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionService questionService;


    @Override
    public Answer saveAnswer(CreateAnswerRequest answerRequest) throws NotFoundException {
        Answer answer = Answer.builder()
                .text(answerRequest.getText())
                .question(questionService.findQuestionById(answerRequest.getQuestionId()))
                .isCorrect(answerRequest.getIsCorrect())
                .build();
        log.info("Creating Answer");
        answerRepository.save(answer);
        return answer;
    }

    @Override
    public List<AnswerResponse> getAllAnswersByQuestionId(UUID questionId) {
        log.info("Get Answer By Id");
        List<Answer> answerList = answerRepository.findAllByQuestionId(questionId);
        return answerList.stream().map(AnswerResponse:: fromAnswer).collect(Collectors.toList());
    }


}
