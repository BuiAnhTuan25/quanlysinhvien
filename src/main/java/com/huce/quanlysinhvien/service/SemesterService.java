package com.huce.quanlysinhvien.service;

import com.huce.quanlysinhvien.constains.SemesterEnum;
import com.huce.quanlysinhvien.constains.StatusEnum;
import com.huce.quanlysinhvien.constains.TypeEnum;
import com.huce.quanlysinhvien.model.dto.SemestersDto;
import com.huce.quanlysinhvien.model.response.Data;
import com.huce.quanlysinhvien.model.response.ListData;

public interface SemesterService {
    Data getById(Long id);
    ListData getAll(int page, int pageSize);
    ListData getByTypeAndStatus(TypeEnum type,SemesterEnum status, int page, int pageSize);
    ListData getByType(TypeEnum type, int page, int pageSize);
    Data createSemester(SemestersDto semester);
    Data updateSemester(SemestersDto semester, Long id);
    Data deleteSemester(Long id);
}
