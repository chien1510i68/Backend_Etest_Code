package com.example.ttcn2etest.mocktest.user_exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResultsDTO {
    private long id;

    private float point;
    private String comment;
    private String nameExam ;
    private long time ;
    private int key ;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Timestamp createDate;

    private List<DetailResults> detailResults;


}
