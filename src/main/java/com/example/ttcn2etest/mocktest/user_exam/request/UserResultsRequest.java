package com.example.ttcn2etest.mocktest.user_exam.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResultsRequest {
    private List<Integer> answerKey ;
    private String value ;
    private long questionId;
}
