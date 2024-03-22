package com.quizapp.quiz.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;
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
import com.quizapp.rating.dto.QuizRatingDTO;
import com.quizapp.rating.service.RatingService;
import com.quizapp.result.dto.LeaderboardDTO;
import com.quizapp.result.dto.QuestionResultDTO;
import com.quizapp.result.dto.ResultLeaderboardDTO;
import com.quizapp.result.dto.UserResultsDTO;
import com.quizapp.result.models.Result;
import com.quizapp.result.repository.ResultRepository;
import com.quizapp.result.service.ResultService;
import com.quizapp.take.dto.TakeCreateRequest;
import com.quizapp.take.dto.TakeResponse;
import com.quizapp.take.models.Take;
import com.quizapp.take.repository.TakeRepository;
import com.quizapp.take.service.TakeService;
import com.quizapp.user.dto.UserStatistics;
import com.quizapp.user.models.User;
import com.quizapp.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
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

    private final RatingService ratingService;

    private final ResultService resultService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IAnswerService answerService;

    @Autowired
    public QuizService(@Lazy TakeService takeService, @Lazy RatingService ratingService, @Lazy ResultService resultService) {
        this.takeService = takeService;
        this.ratingService = ratingService;
        this.resultService = resultService;
    }

    public List<QuizResponse> getAllQuiz() throws NotFoundException {
        List<Quiz> allQuiz = quizRepository.findAll();
        List<QuizResponse> quizResponseList = new ArrayList<>();
       for(Quiz quiz : allQuiz){
           QuizResponse quizResponse = QuizResponse.fromQuiz(quiz,ratingService.getRatingByQuizId(quiz.getQuizId()));
           quizResponseList.add(quizResponse);
       }
       return quizResponseList;
    }

    @Override
    public QuizResponse getQuizResponseById(UUID id) throws NotFoundException {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new NotFoundException("Quiz not found."));
        return QuizResponse.fromQuiz(quiz,ratingService.getRatingByQuizId(quiz.getQuizId()));
    }

    @Override
    public Quiz getQuizById(UUID id) throws NotFoundException {
        return quizRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quiz not found."));
    }

    @Override
    public List<QuizResponse> getQuizByCategoryId(UUID categoryId) throws NotFoundException {
        List<Quiz> allQuiz = quizRepository.findAllByCategoryId(categoryId);
        List<QuizResponse> quizResponseList = new ArrayList<>();
        for(Quiz quiz : allQuiz){
            QuizResponse quizResponse = QuizResponse.fromQuiz(quiz,ratingService.getRatingByQuizId(quiz.getQuizId()));
            quizResponseList.add(quizResponse);
        }
        return quizResponseList;
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

    @Override
    public UserStatistics getUserStatistics(Authentication userDetails) throws NotFoundException {
        String email = userService.getCurrentUser(userDetails).getEmail();
        User currentUser = userService.getUserByEmail(email);
        List<Map<String, Object>> userStatsList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        UserStatistics userStatistics = new UserStatistics();
        userStatistics.setUserId(currentUser.getUserId());
        userStatistics.setUserName(currentUser.getUserName());
        userStatistics.setUserImage(currentUser.getUserImage());
        UserResultsDTO recentResult = resultService.getResultsByUserId(currentUser.getUserId());
        map.put("recentResult", recentResult);


        int numberOfQuizzesAttempted = (int)takeService.getUserTakes(currentUser.getUserId()).stream()
                .map(take -> take.getQuiz().getQuizId())
                .distinct() // Remove duplicates
                .count();
        map.put("quizzesAttempted", numberOfQuizzesAttempted);

        List<QuizResponse> quizList = getAllQuiz().stream().filter(quizResponse -> quizResponse.getIsActive().equals(true)).collect(Collectors.toList());;
        Collections.shuffle(quizList);
        QuizResponse quiz = quizList.get(0);
        List<Take> takes = takeService.getTakesByQuizId(quiz.getQuizId());
        double totalScore = 0;
        double averageScore = 0;
        log.info("quiz {}", quiz);
         totalScore = takes.size() > 0 ? takes.stream().mapToDouble(take -> take.getResult() != null ? take.getResult().getScore() : 0).sum() : totalScore;
         averageScore = totalScore != 0 ? totalScore / takes.size() : averageScore;
        map.put("statsQuiz",quiz);
        map.put("quizAverageScore",Math.round(averageScore * 10.0) / 10.0);
// Calculate average time elapsed
        double totalTimeElapsed = 0;
        double averageTimeElapsed = 0;
         totalTimeElapsed = takes.size() > 0 ? takes.stream().mapToDouble(Take::getTimeElapsed).sum() : totalTimeElapsed;
         averageTimeElapsed = totalTimeElapsed != 0 ? totalTimeElapsed / takes.size() : totalTimeElapsed;
        map.put("quizAverageTimeElapsed",Math.round(averageTimeElapsed));
// Calculate number of participants
        int numberOfParticipants = takes.size();
        map.put("numberOfTakes", numberOfParticipants);

// Find the top of leaderboard
        ResultLeaderboardDTO leaderboard = resultService.getQuizLeaderboard(quiz.getQuizId());
        LeaderboardDTO getLeader = leaderboard.getLeaderboardList().size() > 0 ? leaderboard.getLeaderboardList().get(0) : new LeaderboardDTO();
        map.put("quizLeaderboard", getLeader);
        userStatsList.add(map);
        userStatistics.setUserStats(userStatsList);
       return userStatistics;
    }

    public Map<String,Object> addToUserStats(String objectName, Object object){
        Map<String, Object> map = Map.of(objectName,object);
        return map;
    }
    public TakeResponse processQuizSubmission(TakeCreateRequest request) throws NotFoundException, JsonProcessingException {
        Take take = takeService.takeQuiz(request);
        List<QuestionResultDTO> questionResultDTOList = new ArrayList<>();
        User currentUser = userService.getUserById(request.getUserId());
        Quiz currentQuiz = getQuizById(request.getQuizId());

        Result result = new Result();
        result.setQuiz(currentQuiz);
        result.setUser(currentUser);
        int score = 0;
        int questionCount = 0;
        for (AnswerSubmission answerDTO : request.getAnswers()) {
            QuestionResultDTO questionResultDTO = new QuestionResultDTO();
            Question question = questionService.findQuestionById(answerDTO.getQuestionId());
            questionResultDTO.setText(question.getText());
            questionResultDTO.setUserAnswer(question.getAnswers().stream().filter(answer -> answer.getAnswerId().equals(answerDTO.getSelectedAnswerId()))
                    .findFirst().orElseThrow(()-> new NotFoundException("User Answer Not Found")).getText());
            questionResultDTO.setCorrectAnswer(question.getAnswers().stream().filter(answer -> answer.getIsCorrect().equals(true))
                    .findFirst().orElseThrow(()-> new NotFoundException("Correct Answer Not Found")).getText());
            questionResultDTO.setQuizId(currentQuiz.getQuizId());
            questionResultDTO.setTakeId(take.getTakeId());
            questionResultDTO.setIsCorrect(Objects.equals(questionResultDTO.getUserAnswer(), questionResultDTO.getCorrectAnswer()));
            for(Answer answer : question.getAnswers()){
                    if(answer.getAnswerId().equals(answerDTO.getSelectedAnswerId()) && answer.getIsCorrect().equals(true)){
                        score++;
                    }
                }
            questionResultDTOList.add(questionResultDTO);
            questionCount++;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String submission = objectMapper.writeValueAsString(questionResultDTOList);
        result.setScore(score);
        result.setQuestionCount(questionCount);
        result.setTake(take);
        result.setUserSubmission(submission);
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
