package com.huce.quanlysinhvien.model.dto;

import com.huce.quanlysinhvien.constains.SemesterEnum;
import com.huce.quanlysinhvien.constains.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SemestersDto {
    private Long id;

    @NotBlank
    private String semester;

    @NotBlank
    private String year;

    @NotBlank
    private TypeEnum type;

    private SemesterEnum status;
}
