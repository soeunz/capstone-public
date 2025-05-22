package com.veritas.reviewbackend.service;

import com.veritas.reviewbackend.client.MlApiClient;
import com.veritas.reviewbackend.dto.ReviewAnalyzeResponse;
import com.veritas.reviewbackend.dto.ReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MlApiClient mlApiClient;

    public ReviewAnalyzeResponse analyze(List<ReviewRequest> validReviews) {
        // ML 서버에 요청 보내고 응답 받아서 가공
        return mlApiClient.sendForAnalysis(validReviews);
    }

}
