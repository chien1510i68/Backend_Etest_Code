package com.example.ttcn2etest.mocktest.exam.entity;


import com.example.ttcn2etest.mocktest.section.entity.Section;
import com.example.ttcn2etest.mocktest.user_exam.entity.UserResponse;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
@Entity

public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String type;
    private long timeExam;
    @OneToMany(mappedBy = "exam"  , cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Section> sections ;

    @OneToMany (mappedBy = "exam")
    @JsonManagedReference
    private List<UserResponse> user_exam;



}
