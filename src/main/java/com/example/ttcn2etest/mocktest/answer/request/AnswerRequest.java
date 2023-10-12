package com.example.ttcn2etest.mocktest.answer.request;

import lombok.Data;

import java.util.List;
@Data
public class AnswerRequest {
    private long questionID;
    private long answerId;
    private String answer;
    private  int key ;


}
