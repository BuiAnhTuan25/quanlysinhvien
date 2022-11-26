package com.huce.quanlysinhvien.model.dto;

import com.huce.quanlysinhvien.constains.StatusEnum;
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

    @NotBlank
    private Long teacherId;

    @NotBlank
    private String internshipPlace;

    @NotBlank
    private String graduationTopic;

    @NotBlank
    private Long internshipId;

    @NotBlank
    private Long graduationId;

    @NotBlank
    private StatusEnum status;
}
