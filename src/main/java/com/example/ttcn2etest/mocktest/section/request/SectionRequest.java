package com.example.ttcn2etest.mocktest.section.request;

import com.example.ttcn2etest.mocktest.question.request.CreateQuestionRequest;
import lombok.Data;

import java.util.List;

@Data
public class SectionRequest {
    private long id ;
    private long exam_id ;
    private String title;
    private String file ;
    private String type ;
    private String description ;
    private List<CreateQuestionRequest> questions ;
}
