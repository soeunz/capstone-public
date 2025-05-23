package com.veritas.reviewbackend.client;

import com.veritas.reviewbackend.dto.ReviewAnalyzeResponse;
import com.veritas.reviewbackend.dto.ReviewRequest;
import com.veritas.reviewbackend.dto.ReviewResponse;
import com.veritas.reviewbackend.util.JsonlConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MlApiClient {

    private final JsonlConverter jsonlConverter;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://ml-server-url/api/analyze") // 수정 필요
            .build();

    /*
    public ReviewAnalyzeResponse sendForAnalysis(List<ReviewRequest> reviews) {
        // JSON 배열 → JSONL 문자열
        String jsonlBody = jsonlConverter.convertToJsonL(reviews);

        // ML 서버에 POST 요청
        String jsonlResponse = webClient.post()
                .uri("/api/predict")  // 수정 필요
                .header("Content-Type", "text/plain")
                .bodyValue(jsonlBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // JSONL → List<ReviewResponse> 변환
        List<ReviewResponse> result = jsonlConverter.convertFromJsonL(jsonlResponse);

        return new ReviewAnalyzeResponse(result);
    }
     */

    // 테스트용 더미 데이터
    public ReviewAnalyzeResponse sendForAnalysis(List<ReviewRequest> reviews) {
        // 리뷰 리스트 -> JSONL 변환
        String requestJsonl = jsonlConverter.convertToJsonL(reviews);
        System.out.println("변환된 요청 JSONL");
        System.out.println(requestJsonl);

        // 더미 분석 결과 생성
        List<ReviewResponse> result = reviews.stream()
                .map(review -> new ReviewResponse(
                        review.reviewId(),
                        review.rating() < 3
                ))
                .toList();

        // 분석 결과 -> JSONL 변환
        String responseJsonl = jsonlConverter.convertToJsonL(result);
        System.out.println("응답 JSONL");
        System.out.println(responseJsonl);

        // JSONL → 리뷰 분석 결과 리스트 파싱
        List<ReviewResponse> parsed = jsonlConverter.convertFromJsonL(responseJsonl);
        System.out.println("응답 JSONL → 객체 변환 결과");
        parsed.forEach(System.out::println);

        return new ReviewAnalyzeResponse(parsed);
    }

}
