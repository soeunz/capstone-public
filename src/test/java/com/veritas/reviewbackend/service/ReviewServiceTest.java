package com.veritas.reviewbackend.service;

import com.veritas.reviewbackend.client.MlApiClient;
import com.veritas.reviewbackend.domain.AnalyzedReview;
import com.veritas.reviewbackend.domain.AnalyzedReviewRepository;
import com.veritas.reviewbackend.dto.ReviewAnalyzeResponse;
import com.veritas.reviewbackend.dto.ReviewRequest;
import com.veritas.reviewbackend.dto.ReviewResponse;
import com.veritas.reviewbackend.service.validator.MlResponseValidator;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;

import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Transactional
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private AnalyzedReviewRepository reviewRepository;

    @Autowired
    private MlApiClient mlApiClient;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();

        // 분석 요청이 들어오면 항상 r002만 분석된 것처럼 리턴
        Mockito.when(mlApiClient.sendForAnalysis(any()))
                .thenReturn(new ReviewAnalyzeResponse(List.of(
                        new ReviewResponse("r002", true)
                )));
    }

    @Test
    void someCachedSomeNewAnalyzedReviews() { // 일부는 캐시, 일부는 새로 분석된 리뷰를 처리
        // r001은 미리 저장
        AnalyzedReview saved = AnalyzedReview.builder()
                .reviewId("r001")
                .rating(5)
                .content("기존 리뷰")
                .boolImg(true)
                .isAiGenerated(false)
                .build();
        reviewRepository.save(saved);

        // 요청
        List<ReviewRequest> requests = List.of(
                new ReviewRequest("r001", 5, "기존 리뷰", true),
                new ReviewRequest("r002", 2, "새 리뷰", false)
        );

        // 실행
        ReviewAnalyzeResponse result = reviewService.analyze(requests);

        // 검증
        Map<String, Boolean> resultMap = result.results().stream()
                .collect(Collectors.toMap(ReviewResponse::reviewId, ReviewResponse::isAiGenerated));

        Assertions.assertEquals(2, result.results().size());
        Assertions.assertEquals(false, resultMap.get("r001")); // 기존 저장된 값
        Assertions.assertEquals(true, resultMap.get("r002"));  // 분석된 값

        // DB에도 저장됐는지 확인
        Optional<AnalyzedReview> r002 = reviewRepository.findById("r002");
        Assertions.assertTrue(r002.isPresent());
        Assertions.assertTrue(r002.get().isAiGenerated());
    }

    // 테스트 전용 설정
    @TestConfiguration
    static class TestConfig {

        @Bean
        public MlApiClient mlApiClient() {
            return Mockito.mock(MlApiClient.class);
        }

        @Bean
        public MlResponseValidator mlResponseValidator() {
            return new MlResponseValidator(); // 실제 로직 유지
        }
    }

}