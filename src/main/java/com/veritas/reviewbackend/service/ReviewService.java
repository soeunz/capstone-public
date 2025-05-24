package com.veritas.reviewbackend.service;

import com.veritas.reviewbackend.client.MlApiClient;
import com.veritas.reviewbackend.domain.AnalyzedReview;
import com.veritas.reviewbackend.domain.AnalyzedReviewRepository;
import com.veritas.reviewbackend.dto.ReviewAnalyzeResponse;
import com.veritas.reviewbackend.dto.ReviewRequest;
import com.veritas.reviewbackend.dto.ReviewResponse;
import com.veritas.reviewbackend.service.validator.MlResponseValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MlApiClient mlApiClient;
    private final MlResponseValidator mlResponseValidator;
    private final AnalyzedReviewRepository reviewRepository;

    public ReviewAnalyzeResponse analyze(List<ReviewRequest> validReviews) {
        // 리뷰 ID 리스트 추출
        List<String> ids = validReviews.stream()
                .map(ReviewRequest::reviewId)
                .toList();

        // DB에서 이미 분석된 리뷰 찾기
        List<AnalyzedReview> existingReviews = reviewRepository.findByReviewIdIn(ids);
        Map<String, Boolean> existingMap = existingReviews.stream()
                .collect(Collectors.toMap(AnalyzedReview::getReviewId, AnalyzedReview::isAiGenerated));

        // DB에 없는 리뷰만 필터링
        List<ReviewRequest> reviewsToAnalyze = validReviews.stream()
                .filter(r -> !existingMap.containsKey(r.reviewId()))
                .toList();

        final Map<String, Boolean> newResultsMap = new HashMap<>();

        if (!reviewsToAnalyze.isEmpty()) {
            // ML 서버 호출
            ReviewAnalyzeResponse mlResponse = mlApiClient.sendForAnalysis(reviewsToAnalyze);

            // 유효성 검사
            mlResponseValidator.validate(mlResponse.results());

            // ML 결과를 newResultsMap에 저장
            newResultsMap.putAll(
                    mlResponse.results().stream()
                            .collect(Collectors.toMap(
                                    ReviewResponse::reviewId,
                                    ReviewResponse::isAiGenerated
                            ))
            );

            // DB 저장
            List<AnalyzedReview> newResults = reviewsToAnalyze.stream()
                    .map(req -> {
                        Boolean isAi = newResultsMap.get(req.reviewId());
                        if (isAi == null) {
                            throw new IllegalStateException("ML 결과 누락됨: " + req.reviewId());
                        }

                        return AnalyzedReview.builder()
                                .reviewId(req.reviewId())
                                .rating(req.rating())
                                .content(req.content())
                                .boolImg(req.boolimg())
                                .isAiGenerated(isAi)
                                .build();
                    })
                    .toList();

            reviewRepository.saveAll(newResults);
        }

        // 전체 응답 조합
        List<ReviewResponse> allResponses = validReviews.stream()
                .map(req -> {
                    Boolean isAi = existingMap.containsKey(req.reviewId())
                            ? existingMap.get(req.reviewId())
                            : newResultsMap.getOrDefault(req.reviewId(), false);

                    return new ReviewResponse(req.reviewId(), isAi);
                })
                .toList();

        return new ReviewAnalyzeResponse(allResponses);
    }
}
