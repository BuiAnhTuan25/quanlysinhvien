package com.huce.quanlysinhvien.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdatePasswordDto {
    @JsonProperty("old_password")
    @NotBlank
    private String oldPassword;

    @JsonProperty("new_password")
    @NotBlank
    private String newPassword;
}
