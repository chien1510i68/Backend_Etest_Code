package com.example.ttcn2etest.mocktest.user_exam.service;

import com.example.ttcn2etest.mocktest.question.dto.QuestionDTO;
import com.example.ttcn2etest.mocktest.question.entity.Question;
import com.example.ttcn2etest.mocktest.question.repository.QuestionRepository;
import com.example.ttcn2etest.mocktest.user_exam.dto.DetailResults;
import com.example.ttcn2etest.mocktest.user_exam.dto.UserResultsDTO;
import com.example.ttcn2etest.mocktest.user_exam.entity.UserResponse;
import com.example.ttcn2etest.mocktest.user_exam.entity.UserResults;
import com.example.ttcn2etest.mocktest.user_exam.repository.UserResponseRepository;
import com.example.ttcn2etest.mocktest.user_exam.repository.UserResultsRepository;
import com.example.ttcn2etest.mocktest.user_exam.request.UserResultsRequest;
import com.example.ttcn2etest.model.etity.User;
import com.example.ttcn2etest.repository.user.UserRepository;
import com.example.ttcn2etest.response.BaseItemResponse;
import com.example.ttcn2etest.response.BaseListItemResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserResultsServiceIplm implements UserResultsService {
    private final UserResultsRepository userResultsRepository;
    private final UserResponseServiceImplm userResponseServiceImplm;
    private final QuestionRepository questionRepository;
    private final UserResponseRepository userResponseRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public ResponseEntity<?> getUserResultsDetail(long id) {
        Optional<UserResults> results = userResultsRepository.findById(id);
        if (!results.isPresent()) {
            throw new RuntimeException("Khong tim thay id");
        }
        UserResults userResults = results.get();
        List<UserResultsRequest> userResultsRequests = userResponseServiceImplm.convertStringToListUserResults(userResults.getResults());
        List<DetailResults> detailResults = new ArrayList<>();
        for (UserResultsRequest resultsRequest : userResultsRequests) {
            Optional<Question> question = questionRepository.findById(resultsRequest.getQuestionId());
            if (question.isPresent()) {
                DetailResults detailResults1 = DetailResults.builder()
                        .choiceCorrect(question.get().getChoiceCorrect())
                        .choiceUser(resultsRequest.getAnswerKey())
                        .question(mapper.map(question.get(), QuestionDTO.class))
                        .build();
                detailResults.add(detailResults1);
            }
        }

        BaseItemResponse baseItemResponse = new BaseItemResponse();
        baseItemResponse.setSuccess();
        baseItemResponse.setData(UserResultsDTO.builder()
                .comment(userResults.getComment())
                .point(userResults.getPoint())
                .id(userResults.getId())
                .detailResults(detailResults)
                .build());
        return ResponseEntity.ok(baseItemResponse);
    }

    @Override
    public ResponseEntity<?> getListUserResults(long userId) {
        List<UserResultsDTO> results = new ArrayList<>();
        Optional<User> user1 = userRepository.findById(userId);
        if (!user1.isPresent()) {
            throw new RuntimeException("Khong tim thay id ");
        }
        User user = user1.get();
        List<UserResponse> userResponseList = userResponseRepository.findUserResponsesByUser(user);
        for (UserResponse response : userResponseList) {
            results.addAll(response.getResponseUsers().stream().map((rs) -> {
                UserResultsDTO resultsDTO = mapper.map(rs , UserResultsDTO.class );
                resultsDTO.setNameExam(response.getExam().getName());
                resultsDTO.setTime(response.getExam().getTimeExam());
                resultsDTO.setKey((int)resultsDTO.getId());
                return resultsDTO;
            }).collect(Collectors.toList()));
        }


        BaseListItemResponse baseItemResponse = new BaseListItemResponse();
        baseItemResponse.setSuccess();
        baseItemResponse.setResult(results , results.size());
        return ResponseEntity.ok(baseItemResponse);
    }
}
