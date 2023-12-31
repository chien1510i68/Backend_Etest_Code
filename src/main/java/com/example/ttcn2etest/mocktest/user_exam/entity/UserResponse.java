package com.example.ttcn2etest.mocktest.user_exam.entity;


import com.example.ttcn2etest.mocktest.exam.entity.Exam;
import com.example.ttcn2etest.model.etity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
@Entity
public class UserResponse {
    @Id
    private String id = UUID.randomUUID().toString();

    private int count = 0;
    @ManyToOne
    @JoinColumn(name = "exam_id")
    @JsonBackReference
    private Exam exam;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user ;
    @OneToMany(mappedBy = "userResponse" ,cascade = CascadeType.ALL)
    @JsonManagedReference
    List<UserResults> responseUsers ;







}
