package com.huce.quanlysinhvien.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentSearchRequest {
    private String name;
    private String studentCode;
    private Long graduationId;
    private Long internshipId;
    private Long teacherId;
    private String sortBy;
}
