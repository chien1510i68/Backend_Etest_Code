package com.example.ttcn2etest.mocktest.question.repository;

import com.example.ttcn2etest.mocktest.question.dto.QuestionResultDTO;
import com.example.ttcn2etest.mocktest.question.entity.Question;
import com.example.ttcn2etest.mocktest.section.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question , Long> {
   List<Question> findQuestionsBySection(Section  section);
   Question findQuestionById(long id);

}
