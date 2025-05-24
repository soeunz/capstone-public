package com.veritas.reviewbackend.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalyzedReviewRepository extends JpaRepository<AnalyzedReview, String> {

    List<AnalyzedReview> findByReviewIdIn(List<String> reviewIds);

}