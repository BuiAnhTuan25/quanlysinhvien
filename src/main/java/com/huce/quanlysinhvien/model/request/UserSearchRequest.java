package com.huce.quanlysinhvien.model.request;

import com.huce.quanlysinhvien.constains.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchRequest {
    private String name;
    private String username;
    private RoleEnum role;
    private String sortBy;
}
