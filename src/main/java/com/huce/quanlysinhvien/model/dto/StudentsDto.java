package com.huce.quanlysinhvien.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentsDto {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String studentCode;

    @NotBlank
    private String className;

    private Long teacherId;

    private String internshipPlace;

    private String graduationTopic;

    private Long internshipId;

    private Long graduationId;

    private String mark;
}
