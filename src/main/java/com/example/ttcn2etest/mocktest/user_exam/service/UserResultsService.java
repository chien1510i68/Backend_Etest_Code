package com.example.ttcn2etest.mocktest.user_exam.service;

import com.example.ttcn2etest.mocktest.user_exam.dto.UserResultsDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserResultsService {
    ResponseEntity<?> getUserResultsDetail (UUID id );

    ResponseEntity<?> getListUserResults (Long userId) ;
}
