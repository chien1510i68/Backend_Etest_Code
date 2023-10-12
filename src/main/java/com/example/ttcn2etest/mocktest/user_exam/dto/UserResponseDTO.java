package com.example.ttcn2etest.mocktest.user_exam.dto;

import com.example.ttcn2etest.mocktest.exam.entity.Exam;
import com.example.ttcn2etest.mocktest.user_exam.entity.UserResults;
import com.example.ttcn2etest.model.etity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserResponseDTO {
    private long id ;
    private Date createTime;
    private int totalPoint ;
    private int count = 0;

    private Exam exam;

    private User user ;


}
