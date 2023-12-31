package com.example.ttcn2etest.mocktest.user_exam.repository;

import com.example.ttcn2etest.mocktest.user_exam.entity.UserResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserResultsRepository extends JpaRepository<UserResults, UUID> {
}
