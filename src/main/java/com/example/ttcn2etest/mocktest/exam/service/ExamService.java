package com.example.ttcn2etest.mocktest.exam.service;

import com.example.ttcn2etest.mocktest.exam.dto.DetailExamDTO;
import com.example.ttcn2etest.mocktest.exam.dto.ExamDTO;
import com.example.ttcn2etest.mocktest.exam.entity.Exam;
import com.example.ttcn2etest.mocktest.exam.request.ExamRequest;
import com.example.ttcn2etest.mocktest.section.entity.Section;
import com.example.ttcn2etest.mocktest.section.request.SectionRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ExamService {
    Exam createExam(ExamRequest request);

    Exam updateExam(ExamRequest request);

    boolean deleteExam(String id);

    ResponseEntity<?> getAllExamFree();

    DetailExamDTO getByID(String id);

    List<DetailExamDTO> listDetailExam();

    ExamDTO addSectionToExam(SectionRequest sectionRequest);

    List<Section> findQuestionByType(String id , String type) ;

    Exam createExamByExcel(String path) ;

}
