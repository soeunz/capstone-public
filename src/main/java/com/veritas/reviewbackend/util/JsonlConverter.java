package com.veritas.reviewbackend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.veritas.reviewbackend.dto.ReviewResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JsonlConverter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> String convertToJsonL(List<T> objects) {
        return objects.stream()
                .map(this::toJson)
                .collect(Collectors.joining("\n"));
    }

    public List<ReviewResponse> convertFromJsonL(String jsonl) {
        return Arrays.stream(jsonl.split("\n"))
                .filter(line -> !line.isBlank())
                .map(this::fromJson)
                .toList();
    }

    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 변환 실패", e);
        }
    }

    private ReviewResponse fromJson(String line) {
        try {
            return objectMapper.readValue(line, ReviewResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSONL 파싱 실패", e);
        }
    }

}
