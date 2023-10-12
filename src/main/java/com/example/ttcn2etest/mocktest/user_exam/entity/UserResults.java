package com.example.ttcn2etest.mocktest.user_exam.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
@Entity
public class UserResults {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    private Date createDate ;

    @Column(columnDefinition = "text")
    private String results ;
    private float point ;
    @Column(columnDefinition = "text")
    private String comment ;
    @ManyToOne
    @JoinColumn(name = "user_response_id")
    private UserResponse userResponse;
}
