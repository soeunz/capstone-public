package com.veritas.reviewbackend.client;

import com.veritas.reviewbackend.dto.ReviewAnalyzeResponse;
import com.veritas.reviewbackend.dto.ReviewRequest;
import com.veritas.reviewbackend.dto.ReviewResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class MlApiClient {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://ml-server-url/api/predict")
            .build();

    /*
    public ReviewAnalyzeResponse sendForAnalysis(List<ReviewRequest> reviews) {
        // ML 서버에 POST 요청
        return webClient.post()
                .bodyValue(reviews)
                .retrieve()
                .bodyToMono(ReviewAnalyzeResponse.class)
                .block();
    }
     */

    // 테스트용 더미 데이터
    public ReviewAnalyzeResponse sendForAnalysis(List<ReviewRequest> reviews) {
        List<ReviewResponse> result = reviews.stream().map(review ->
                new ReviewResponse(review.reviewId(), "FAKE", 0.91)
        ).toList();

        return new ReviewAnalyzeResponse(result);
    }

}
