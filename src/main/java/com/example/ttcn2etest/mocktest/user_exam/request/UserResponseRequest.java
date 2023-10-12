package com.example.ttcn2etest.mocktest.user_exam.request;

import com.example.ttcn2etest.mocktest.user_exam.entity.UserResults;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseRequest {
    private long id ;
    private int totalPoint ;
    private int count ;
    private long exam_id;
    private long user_id ;

    List<UserResultsRequest> responseUsers ;
//    private String responseUsers ;
}
