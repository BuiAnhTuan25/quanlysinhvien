package com.huce.quanlysinhvien.service;

import com.huce.quanlysinhvien.constains.TypeEnum;
import com.huce.quanlysinhvien.model.dto.StudentsDto;
import com.huce.quanlysinhvien.model.request.StudentSearchRequest;
import com.huce.quanlysinhvien.model.response.Data;
import com.huce.quanlysinhvien.model.response.ListData;

public interface StudentService {
    Data getById(Long id);
    ListData search(StudentSearchRequest request, int page, int pageSize);
    ListData searchSemesterNull(TypeEnum type, int page, int pageSize);
    ListData getListStudentByInternshipId(Long internshipId, int page, int pageSize);

    ListData getListStudentByGraduationId(Long graduationId, int page, int pageSize);

    ListData statisticGraduation(Long graduationId, int page, int pageSize);

    ListData statisticInternship(Long internshipId, int page, int pageSize);

    Data createStudent(StudentsDto user);

    Data updateStudent(StudentsDto user, Long id);

    Data deleteStudent(Long id);
}
