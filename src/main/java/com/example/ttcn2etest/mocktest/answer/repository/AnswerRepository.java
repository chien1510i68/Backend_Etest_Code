package com.example.ttcn2etest.mocktest.answer.repository;

import com.example.ttcn2etest.mocktest.answer.entity.Answer;
import com.example.ttcn2etest.mocktest.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository <Answer , Long> {
    List<Answer> findAnswersByQuestion(Question question);
}
