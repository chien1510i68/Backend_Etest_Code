package com.example.ttcn2etest.mocktest.exam.repository;

import com.example.ttcn2etest.mocktest.exam.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long > {
    Exam findExamById(long id );

}
