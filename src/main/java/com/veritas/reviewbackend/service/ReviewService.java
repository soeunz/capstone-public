package com.veritas.reviewbackend.service;

import com.veritas.reviewbackend.client.MlApiClient;
import com.veritas.reviewbackend.dto.ReviewAnalyzeRequest;
import com.veritas.reviewbackend.dto.ReviewAnalyzeResponse;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final MlApiClient mlApiClient;

    public ReviewService(MlApiClient mlApiClient) {
        this.mlApiClient = mlApiClient;
    }

    public ReviewAnalyzeResponse analyze(ReviewAnalyzeRequest request) {
        // ML 서버에 요청 보내고 응답 받아서 가공
        return mlApiClient.sendForAnalysis(request.reviews());
    }

}
