package com.example.ttcn2etest.mocktest.user_exam.service;

import com.example.ttcn2etest.mocktest.user_exam.dto.UserResponseDTO;
import com.example.ttcn2etest.mocktest.user_exam.entity.UserResponse;
import com.example.ttcn2etest.mocktest.user_exam.request.UserResponseRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserResponseService {
    UserResponseDTO createUserResponse (UserResponseRequest request);
    ResponseEntity updateUserResponse (UserResponseRequest request);

    ResponseEntity addUserResponse(UserResponseRequest request );

    boolean deleteUserResponse(long id ) ;



    List<UserResponse> listUserResponse();

    UserResponse getUserResponseById(long id) ;

}
