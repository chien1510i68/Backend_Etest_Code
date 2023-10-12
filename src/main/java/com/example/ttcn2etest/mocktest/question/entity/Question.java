package com.example.ttcn2etest.mocktest.question.entity;

import com.example.ttcn2etest.mocktest.answer.entity.Answer;
import com.example.ttcn2etest.mocktest.section.entity.Section;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    @Column(name = "content")
    private String content ;

    @Column(name =  "point")
    private Float point ;

    @Column(name =  "questionType")
    private String questionType;
    @Column(name = "choiceCorrect")
    @ElementCollection
    private List<Integer> choiceCorrect = new ArrayList<>();


    @Column(columnDefinition = "text")
    private  String description ;


//
    @ManyToOne
    @JoinColumn(name = "section_id" )
    @JsonBackReference
    private Section section ;

    @OneToMany(mappedBy = "question" ,cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Answer> listAnswer = new ArrayList<>();


}
