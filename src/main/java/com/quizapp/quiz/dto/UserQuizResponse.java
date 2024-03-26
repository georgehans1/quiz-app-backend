package com.quizapp.quiz.dto;

import com.quizapp.category.dto.CategoryResponse;
import com.quizapp.question.dto.QuestionResponse;
import com.quizapp.question.models.Question;
import com.quizapp.quiz.models.Quiz;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuizResponse {
    private UUID quizId;
    private List<QuestionResponse> questionList;

    public static UserQuizResponse fromUserQuizResponse(Quiz quiz){
        List<QuestionResponse> questionResponse = new ArrayList<>();
        for(Question question : quiz.getQuestions()){
            questionResponse.add(QuestionResponse.fromUserQuestion(question));
        }

        return new UserQuizResponse(
                quiz.getQuizId(),
                questionResponse
        );
    }
}
