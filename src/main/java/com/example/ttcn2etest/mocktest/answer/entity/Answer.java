package com.example.ttcn2etest.mocktest.answer.entity;

import com.example.ttcn2etest.mocktest.question.entity.Question;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
@Entity

public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String answer;
    private Integer answerKey;

    @ManyToOne
    @JoinColumn(name = "question_id" )
    @JsonBackReference
    private Question question;

    public Answer(String answer, Integer answerKey, Question question) {
        this.answer = answer;
        this.answerKey = answerKey;
        this.question = question;
    }
}
