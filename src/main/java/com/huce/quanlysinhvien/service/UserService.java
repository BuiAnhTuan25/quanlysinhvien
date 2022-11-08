package com.huce.quanlysinhvien.service;

import com.huce.quanlysinhvien.model.dto.UpdatePasswordDto;
import com.huce.quanlysinhvien.model.dto.UsersDto;
import com.huce.quanlysinhvien.model.response.Data;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    Data getById(Long id);

    Data createUser(UsersDto user);

    Data updateUser(UsersDto user, Long id);

    Data deleteUser(Long id);

    UserDetails loadUserByUsername(String username);

    UserDetails loadUserById(Long id);

    Data changePassword(Long id, UpdatePasswordDto passwordDto);
}
