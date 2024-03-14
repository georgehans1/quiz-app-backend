package com.quizapp.quiz.controller;

import com.quizapp.common.exceptions.NotFoundException;
import com.quizapp.quiz.dto.QuizCreateRequest;
import com.quizapp.quiz.dto.QuizResponse;
import com.quizapp.quiz.dto.UserQuizResponse;
import com.quizapp.quiz.service.IQuizService;
import com.quizapp.take.dto.TakeCreateRequest;
import com.quizapp.take.dto.TakeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private IQuizService quizService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<QuizResponse>> getQuiz(){
        return ResponseEntity.ok(quizService.getAllQuiz());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity saveQuiz(@RequestBody QuizCreateRequest quiz) throws NotFoundException{
        quizService.saveQuiz(quiz);
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", "Quiz created successfully"));
    }

    @PostMapping("/submit")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TakeResponse> processQuizSubmission(@RequestBody TakeCreateRequest createRequest) throws NotFoundException{
        TakeResponse response = quizService.processQuizSubmission(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{quizId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<QuizResponse> getQuizById(@PathVariable UUID quizId) throws NotFoundException {
        return ResponseEntity.ok(quizService.getQuizResponseById(quizId));
    }

    @GetMapping("/userQuiz/{quizId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserQuizResponse> getUserQuiz(@PathVariable UUID quizId) throws NotFoundException {
        return ResponseEntity.ok(quizService.userQuizObject(quizId));
    }


    @GetMapping("/category/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<QuizResponse>> getAllQuizByCategoryId(@PathVariable UUID id){
        return ResponseEntity.ok(quizService.getQuizByCategoryId(id));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void editQuiz(@PathVariable UUID id, @RequestBody QuizCreateRequest quiz) throws NotFoundException {
        log.info("Quiz {}", quiz);
        quizService.editQuiz(id, quiz);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteQuiz(@PathVariable UUID id) throws NotFoundException {
        log.info("Quiz {}", id);
        quizService.deleteQuiz(id);
    }

    @PatchMapping("/{quizId}/toggle-active")
    public ResponseEntity<?> toggleQuizActiveState(@PathVariable UUID quizId) throws NotFoundException {
        quizService.changeQuizActiveStatus(quizId);
        return ResponseEntity.ok().build();
    }

}
