package com.quizapp.rating.service;

import com.quizapp.common.exceptions.NotFoundException;
import com.quizapp.quiz.service.QuizService;
import com.quizapp.rating.dto.CreateRatingRequest;
import com.quizapp.rating.dto.QuizRatingDTO;
import com.quizapp.rating.dto.RatingDTO;
import com.quizapp.rating.models.Rating;
import com.quizapp.rating.repository.RatingRepository;
import com.quizapp.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RatingService implements IRatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserService userService;

    @Override
    public Rating saveRating(CreateRatingRequest createRatingRequest) throws NotFoundException {
        log.info("Request {}", createRatingRequest);
        Rating rating = Rating.builder()
                .quiz(quizService.getQuizById(createRatingRequest.getQuizId()))
                .user(userService.getUserById(createRatingRequest.getUserId()))
                .description(createRatingRequest.getDescription())
                .rating(createRatingRequest.getRating())
                .description(createRatingRequest.getDescription())
                .text(createRatingRequest.getText())
                .build();
            ratingRepository.save(rating);
        return rating;
    }

    @Override
    public QuizRatingDTO getRatingByQuizId(UUID quizId) throws NotFoundException {
        QuizRatingDTO quizRatingDTO = new QuizRatingDTO();
        quizRatingDTO.setQuizId(quizId);

        List<RatingDTO> ratingList = ratingRepository.findAllByQuizId(quizId).stream()
                .map(RatingDTO::fromRating)
                .sorted(Comparator.comparing(RatingDTO::getCreatedAt).reversed())
                .collect(Collectors.toList());

        // Initialize rating numbers map with all ratings from 1 to 5
        Map<Integer, Integer> ratingNumbers = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            ratingNumbers.put(i, 0);
        }

        // Update counts for actual ratings
        for (RatingDTO ratingDTO : ratingList) {
            ratingNumbers.put(ratingDTO.getRating(), ratingNumbers.getOrDefault(ratingDTO.getRating(), 0) + 1);
        }

        // Calculate average rating
        double averageRating = ratingList.stream()
                .mapToInt(RatingDTO::getRating)
                .average()
                .orElse(0.0); // Default to 0 if there are no ratings
        quizRatingDTO.setAverageRating((int) Math.round(averageRating));

        quizRatingDTO.setRatingNumbers(ratingNumbers);
        quizRatingDTO.setRatingCount(ratingList.size());
        quizRatingDTO.setRatingDTOList(ratingList);

        return quizRatingDTO;
    }
}
