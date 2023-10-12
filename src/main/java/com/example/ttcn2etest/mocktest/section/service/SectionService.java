package com.example.ttcn2etest.mocktest.section.service;

import com.example.ttcn2etest.mocktest.exam.entity.Exam;
import com.example.ttcn2etest.mocktest.section.entity.Section;
import com.example.ttcn2etest.mocktest.section.request.SectionRequest;

import java.util.List;


public interface SectionService {
     Section createSection(SectionRequest request);

     Section updateSection (SectionRequest request);

     boolean deleteSection (long id);

     List<Section> getAllSection();

     Section createSectionInExam(SectionRequest sectionRequest );




}
