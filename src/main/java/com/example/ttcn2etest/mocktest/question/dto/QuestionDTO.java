package com.example.ttcn2etest.mocktest.question.dto;

import com.example.ttcn2etest.mocktest.answer.entity.Answer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

//@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private long id;
    private String content;
    private Integer point;
    private String questionType;
    private String description;
    private List<Answer> listAnswer;

    public List<Answer> getListAnswer() {
        return listAnswer;
    }

    public void setListAnswer(List<Answer> listAnswer) {
        this.listAnswer = listAnswer;
    }

    @JsonIgnore
    private List<Integer> choiceCorrect;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public List<Integer> getChoiceCorrect() {
        return choiceCorrect;
    }

    public void setChoiceCorrect(List<Integer> choiceCorrect) {
        this.choiceCorrect = choiceCorrect;
    }
}
