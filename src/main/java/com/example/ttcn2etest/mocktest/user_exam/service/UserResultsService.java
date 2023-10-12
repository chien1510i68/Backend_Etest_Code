package com.example.ttcn2etest.mocktest.user_exam.service;

import com.example.ttcn2etest.mocktest.user_exam.dto.UserResultsDTO;
import org.springframework.http.ResponseEntity;

public interface UserResultsService {
    ResponseEntity<?> getUserResultsDetail (long id );

    ResponseEntity<?> getListUserResults (long userId) ;
}
