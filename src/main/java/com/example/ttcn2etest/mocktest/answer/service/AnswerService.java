package com.example.ttcn2etest.mocktest.answer.service;

import com.example.ttcn2etest.mocktest.answer.entity.Answer;
import com.example.ttcn2etest.mocktest.answer.request.AnswerRequest;

public interface AnswerService {
    Answer createAnswer(AnswerRequest request);
    Answer updateAnswer(AnswerRequest request);

    boolean deleteAnswer(long id);

}
