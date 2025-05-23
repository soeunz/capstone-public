package com.veritas.reviewbackend.service;

import com.veritas.reviewbackend.client.MlApiClient;
import com.veritas.reviewbackend.dto.ReviewAnalyzeResponse;
import com.veritas.reviewbackend.dto.ReviewRequest;
import com.veritas.reviewbackend.service.validator.MlResponseValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MlApiClient mlApiClient;
    private final MlResponseValidator mlResponseValidator;

    public ReviewAnalyzeResponse analyze(List<ReviewRequest> validReviews) {
        // ML 서버에 요청 보내고 응답 받기
        ReviewAnalyzeResponse response = mlApiClient.sendForAnalysis(validReviews);

        // 응답 유효성 검사
        mlResponseValidator.validate(response.results());

        return response;
    }

}
