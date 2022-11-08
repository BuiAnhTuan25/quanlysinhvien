package com.huce.quanlysinhvien.model.dto;

import com.huce.quanlysinhvien.constains.RoleEnum;
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
public class UsersDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    private RoleEnum role;

    private StatusEnum status;

}
