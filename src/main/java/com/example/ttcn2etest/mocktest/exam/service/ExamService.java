package com.example.ttcn2etest.mocktest.exam.service;

import com.example.ttcn2etest.mocktest.exam.dto.DetailExamDTO;
import com.example.ttcn2etest.mocktest.exam.dto.ExamDTO;
import com.example.ttcn2etest.mocktest.exam.entity.Exam;
import com.example.ttcn2etest.mocktest.exam.request.ExamRequest;
import com.example.ttcn2etest.mocktest.question.entity.Question;
import com.example.ttcn2etest.mocktest.section.entity.Section;
import com.example.ttcn2etest.mocktest.section.request.SectionRequest;

import java.util.List;

public interface ExamService {
    Exam createExam(ExamRequest request);

    Exam updateExam(ExamRequest request);

    boolean deleteExam(long id);

    List<ExamDTO> getAllExam();

    DetailExamDTO getByID(long id);

    List<DetailExamDTO> listDetailExam();

    ExamDTO addSectionToExam(SectionRequest sectionRequest);

    List<Section> findQuestionByType(long id , String type) ;

    Exam createExamByExcel(String path) ;

}
