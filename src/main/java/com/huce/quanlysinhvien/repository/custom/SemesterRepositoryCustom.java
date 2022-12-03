package com.huce.quanlysinhvien.repository.custom;

import com.huce.quanlysinhvien.model.entity.SemestersEntity;
import com.huce.quanlysinhvien.model.request.SemesterSearchRequest;

import java.util.List;

public interface SemesterRepositoryCustom {
    List<SemestersEntity> search(SemesterSearchRequest request, int page, int pageSize);
    Long count(SemesterSearchRequest request);
}
