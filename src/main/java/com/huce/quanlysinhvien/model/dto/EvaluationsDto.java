package com.huce.quanlysinhvien.model.dto;

import com.huce.quanlysinhvien.constains.SemesterEnum;
import com.huce.quanlysinhvien.constains.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvaluationsDto {
    private Long id;

    @NotBlank
    private String topic;

    @NotBlank
    private String mark;

    private String note;

    @NotBlank
    private Long studentId;

    @NotBlank
    private TypeEnum type;
}
