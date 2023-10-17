package com.example.ttcn2etest.mocktest.user_exam.controller;

import com.example.ttcn2etest.mocktest.user_exam.service.UserResultsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("mocktest/results/")
@RequiredArgsConstructor
public class UserResultsController {
    private  final UserResultsService userResultsService ;
    @GetMapping("{id}")
    public ResponseEntity<?> getResultsUser(@PathVariable UUID id){
        return ResponseEntity.ok(userResultsService.getUserResultsDetail(id));
    }
    @GetMapping("user/{id}")
    public ResponseEntity<?> getListResults(@PathVariable Long id){
        return userResultsService.getListUserResults(id);
    }
}
