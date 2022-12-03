package com.huce.quanlysinhvien.repository.custom;

import com.huce.quanlysinhvien.model.entity.StudentsEntity;
import com.huce.quanlysinhvien.model.request.StudentSearchRequest;

import java.util.List;

public interface StudentRepositoryCustom {
    List<StudentsEntity> search(StudentSearchRequest request, int page, int pageSize);
    Long count(StudentSearchRequest request);
}
