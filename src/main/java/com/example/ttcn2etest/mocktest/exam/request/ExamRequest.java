package com.example.ttcn2etest.mocktest.exam.request;

import com.example.ttcn2etest.mocktest.section.request.SectionRequest;
import lombok.Data;

import java.util.List;

@Data

public class ExamRequest {
    private  String name ;
    private long id ;
    private long timeExam ;
    List<SectionRequest> sectionRequests ;

}
