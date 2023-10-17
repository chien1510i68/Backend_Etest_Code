package com.example.ttcn2etest.mocktest;

import com.example.ttcn2etest.mocktest.exam.dto.DetailExamDTO;
import com.example.ttcn2etest.mocktest.exam.request.UserTestRequest;
import com.example.ttcn2etest.mocktest.exam.service.ExamService;
import com.example.ttcn2etest.mocktest.section.entity.Section;
import com.example.ttcn2etest.response.BaseListItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client/exam/")
public class ClientController {
    private final ExamService examService ;
    @GetMapping("all")
    public ResponseEntity<?> getAllExam(){

        return ResponseEntity.ok(examService.getAllExamFree());
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getExamByID(@PathVariable String id) {
        DetailExamDTO exam = examService.getByID(id);
        return ResponseEntity.ok(exam);
    }
    @PostMapping("detail")
    public ResponseEntity<?> findSections(@RequestBody UserTestRequest request) {
        BaseListItemResponse response = new BaseListItemResponse();
        response.setSuccess(true);
        List<Section> sections = examService.findQuestionByType(request.getId(), request.getType());
        response.setResult(sections, sections.size());
        return ResponseEntity.ok(response);

    }
}
