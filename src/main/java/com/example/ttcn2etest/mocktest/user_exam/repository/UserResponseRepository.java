package com.example.ttcn2etest.mocktest.user_exam.repository;

import com.example.ttcn2etest.mocktest.exam.entity.Exam;
import com.example.ttcn2etest.model.etity.User;
import com.example.ttcn2etest.mocktest.user_exam.entity.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserResponseRepository  extends JpaRepository<UserResponse , Long> {
    List<UserResponse> findUserResponsesByUserAndExam(User user , Exam exam);
    Optional<UserResponse> findUserResponseByUserAndExam(User user , Exam exam);

    List<UserResponse> findUserResponsesByUser(User user);
}
