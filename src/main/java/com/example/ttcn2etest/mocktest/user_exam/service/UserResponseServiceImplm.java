package com.example.ttcn2etest.mocktest.user_exam.service;

import com.example.ttcn2etest.mocktest.exam.entity.Exam;
import com.example.ttcn2etest.mocktest.exam.repository.ExamRepository;
import com.example.ttcn2etest.mocktest.question.entity.Question;
import com.example.ttcn2etest.mocktest.question.repository.QuestionRepository;
import com.example.ttcn2etest.mocktest.user_exam.dto.UserResponseDTO;
import com.example.ttcn2etest.mocktest.user_exam.dto.UserResultsDTO;
import com.example.ttcn2etest.mocktest.user_exam.entity.UserResponse;
import com.example.ttcn2etest.mocktest.user_exam.entity.UserResults;
import com.example.ttcn2etest.mocktest.user_exam.repository.UserResponseRepository;
import com.example.ttcn2etest.mocktest.user_exam.repository.UserResultsRepository;
import com.example.ttcn2etest.mocktest.user_exam.request.UserResponseRequest;
import com.example.ttcn2etest.mocktest.user_exam.request.UserResultsRequest;
import com.example.ttcn2etest.model.etity.User;
import com.example.ttcn2etest.repository.user.UserRepository;
import com.example.ttcn2etest.response.BaseItemResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserResponseServiceImplm implements UserResponseService {
    private final UserResponseRepository userResponseRepository;
    private final UserRepository userRepository;
    private final ExamRepository examRepository;
    private final UserResultsRepository userResultsRepository;

    private final QuestionRepository questionRepository;
    private final ModelMapper mapper;


    private static final Logger logger = LogManager.getLogger(UserResponseServiceImplm.class);


    @Transactional
    public ResponseEntity<?> addUserResponse(UserResponseRequest request) {
        Exam exam = examRepository.findExamById(request.getExam_id());
        User user = userRepository.findUserById(request.getUser_id());
        List<UserResults> userResults = new ArrayList<>();
        Optional<UserResponse> userResponse = userResponseRepository.findUserResponseByUserAndExam(user, exam);

        if (!userResponse.isPresent()) {
            UserResponse response = UserResponse.builder()
                    .count(0)

                    .exam(exam)
                    .user(user)
                    .build();
            userResponseRepository.save(response);
            UserResults userResults1 = UserResults.builder().results(convertUserResultsListToJsonString(request.getResponseUsers()))
                    .userResponse(response)
                    .createDate(new Date())
                    .build();
            calPoint(request, userResults1);
            userResults1.setComment(commentResults(userResults1.getPoint()));
            userResultsRepository.save(userResults1);
            userResults.add(userResults1);
            response.setResponseUsers(userResults);
            BaseItemResponse itemResponse = new BaseItemResponse();
            itemResponse.setSuccess();
            itemResponse.setData(mapper.map(userResults1, UserResultsDTO.class));

            return ResponseEntity.ok(itemResponse);
        }

        UserResponse existingResponse = userResponse.get();
        existingResponse.setCount(existingResponse.getCount() + 1);

        existingResponse.setExam(exam);
        existingResponse.setUser(user);
        existingResponse.setCount(existingResponse.getCount() + 1);
        UserResults userResults1 = UserResults.builder()
                .userResponse(existingResponse)
                .createDate(new Date())
                .results(convertUserResultsListToJsonString(request.getResponseUsers())).build();
        userResults = existingResponse.getResponseUsers();
        userResults.add(userResults1);
        calPoint(request, userResults1);
        userResults1.setComment(commentResults(userResults1.getPoint()));

        userResultsRepository.save(userResults1);
        existingResponse.setResponseUsers(userResults);
        userResponseRepository.save(existingResponse);
        BaseItemResponse response = new BaseItemResponse();
        response.setSuccess();
        response.setData(mapper.map(userResults1, UserResultsDTO.class));

        return ResponseEntity.ok(response);
    }

    public String convertUserResultsListToJsonString(List<UserResultsRequest> userResultsRequestList) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(userResultsRequestList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }

    public List<UserResultsRequest> convertStringToListUserResults (String s){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
           return objectMapper.readValue(s, new TypeReference<List<UserResultsRequest>>() {});
        }  catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String commentResults(Float point) {
        if (point < 4) {
            return "Bài thi của bạn chưa được tốt lắm";
        } else if (point < 8) {
            return "Bạn cần ôn tập thêm các kiến thức";
        } else if (point > 8) {
            return "Bài thi của bạn đạt kết quả tốt";
        } else {
            return "Cạn lời ";
        }
    }

    public void calPoint(UserResponseRequest request, UserResults userResults) {
        for (UserResultsRequest resultsRequest : request.getResponseUsers()) {
            Optional<Question> question = questionRepository.findById(resultsRequest.getQuestionId());
            if (question.isPresent() && resultsRequest.getAnswerKey() != null && question.get().getChoiceCorrect().containsAll(resultsRequest.getAnswerKey()))
                userResults.setPoint(userResults.getPoint() + question.get().getPoint());
        }
    }


    @Override
    public UserResponseDTO createUserResponse(UserResponseRequest request) {
        return null;
    }

    @Override
    public ResponseEntity updateUserResponse(UserResponseRequest request) {
        Exam exam = examRepository.findExamById(request.getExam_id());
        User user = userRepository.findUserById(request.getUser_id());
        Optional<UserResponse> userResponse = userResponseRepository.findUserResponseByUserAndExam(user, exam);
        if (!userResponse.isPresent()) {
            throw new RuntimeException("Không tìm thấy userresponse");
        }
        userResponse.get().setCount(request.getCount());
        userResponseRepository.save(userResponse.get());
        return ResponseEntity.ok("Thanh cong cap nhat ");
    }

    @Override
    public boolean deleteUserResponse(UUID id) {
        return false;
    }

    @Override
    public List<UserResponse> listUserResponse() {
        return null;
    }

    @Override
    public UserResponse getUserResponseById(long id) {
        Optional<UserResponse> response = userResponseRepository.findById(id) ;
        return response.get();
    }
}
