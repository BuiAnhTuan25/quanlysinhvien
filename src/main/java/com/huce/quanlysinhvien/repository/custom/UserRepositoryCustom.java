package com.huce.quanlysinhvien.repository.custom;

import com.huce.quanlysinhvien.model.entity.UsersEntity;
import com.huce.quanlysinhvien.model.request.UserSearchRequest;

import java.util.List;

public interface UserRepositoryCustom {
    List<UsersEntity> search(UserSearchRequest request, int page, int pageSize);
    Long count(UserSearchRequest request);
}
