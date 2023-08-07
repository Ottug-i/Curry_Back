package com.ottugi.curry.service.recommend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.web.dto.recommend.RatingRequestDto;
import com.ottugi.curry.web.dto.recommend.RatingResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {

    private final String FLASK_API_URL = "http://localhost:5000";
    private final UserRepository userRepository;

    @Override
    public List<Long> getRecommendBookmark(Long recipeId, int page) throws JsonProcessingException {

        String apiUrl = String.format("%s/bookmark/recommend?recipe_id=%d&page=%d", FLASK_API_URL, recipeId, page);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        String response = restTemplate.getForObject(apiUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        Long[] result = objectMapper.readValue(response, Long[].class);

        if (response != null) {
            return Arrays.asList(result);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public RatingResponseDto getUserRating(Long userId, Long recipeId) throws JsonProcessingException {

        String apiUrl = String.format("%s/rating/user_ratings?user_id=%d&recipe_id=%d", FLASK_API_URL, userId, recipeId);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        String response = restTemplate.getForObject(apiUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Double[] result = objectMapper.readValue(response, Double[].class);
            return new RatingResponseDto(Arrays.asList(result));
        } catch (MismatchedInputException e) {
            return null;
        }
    }

    @Override
    public Boolean updateUserRating(RatingRequestDto ratingRequestDto) {

        String apiUrl = String.format("%s/rating/user_ratings", FLASK_API_URL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RatingRequestDto> httpEntity = new HttpEntity<>(ratingRequestDto, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, httpEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Long> getRecommendRating(Long userId, int page, Long[] bookmarkList) throws JsonProcessingException {

        String apiUrl = String.format("%s/rating/recommend?user_id=%d&page=%d", FLASK_API_URL, userId, page);
        for (Long bookmarkId : bookmarkList) {
            apiUrl += "&bookmark_list=" + bookmarkId;
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        String response = restTemplate.getForObject(apiUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        Object[] resultList = objectMapper.readValue(response, Object[].class);

        log.info(String.valueOf(resultList));
        log.info((String) resultList[0]);
        log.info(String.valueOf(resultList[1]));

        String genre = (String) resultList[0];
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        user.updateGenre(genre);

        Long[] result = objectMapper.convertValue(resultList[1], Long[].class);

        if (response != null) {
            return Arrays.asList(result);
        } else {
            return Collections.emptyList();
        }
    }
}