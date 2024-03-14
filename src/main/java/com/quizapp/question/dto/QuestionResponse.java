package com.quizapp.question.dto;

import com.quizapp.answer.dto.AnswerResponse;
import com.quizapp.answer.models.Answer;
import com.quizapp.category.dto.CategoryResponse;
import com.quizapp.question.models.Question;
import com.quizapp.quiz.dto.QuizResponse;
import com.quizapp.quiz.models.Quiz;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {
    private UUID questionId;
    private UUID quizId;
    private String text;
    private Timestamp createdAt;
    private List<AnswerResponse> answerList;

    public static QuestionResponse fromQuestion(Question question)
    {
        List<AnswerResponse> answerResponses = new ArrayList<>();
        for (Answer answer : question.getAnswers()) {
            answerResponses.add(AnswerResponse.fromAnswer(answer));
        }
        return new QuestionResponse(
                question.getQuestionId(),
                question.getQuiz().getQuizId(),
                question.getText(),
                question.getCreatedAt(),
                answerResponses
        );
    }

    public static QuestionResponse fromUserQuestion(Question question)
    {
        List<AnswerResponse> answerResponses = new ArrayList<>();
        for (Answer answer : question.getAnswers()) {
            answerResponses.add(AnswerResponse.fromUserAnswer(answer));
        }
        return new QuestionResponse(
                question.getQuestionId(),
                question.getQuiz().getQuizId(),
                question.getText(),
                question.getCreatedAt(),
                answerResponses
        );
    }
}
