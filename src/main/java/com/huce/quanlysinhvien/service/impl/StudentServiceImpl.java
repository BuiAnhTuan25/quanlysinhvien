package com.huce.quanlysinhvien.service.impl;

import com.huce.quanlysinhvien.constains.SemesterEnum;
import com.huce.quanlysinhvien.constains.TypeEnum;
import com.huce.quanlysinhvien.model.dto.StudentsDto;
import com.huce.quanlysinhvien.model.dto.UsersDto;
import com.huce.quanlysinhvien.model.entity.StudentsEntity;
import com.huce.quanlysinhvien.model.request.StudentSearchRequest;
import com.huce.quanlysinhvien.model.response.Data;
import com.huce.quanlysinhvien.model.response.ListData;
import com.huce.quanlysinhvien.model.response.Pagination;
import com.huce.quanlysinhvien.model.response.Response;
import com.huce.quanlysinhvien.repository.StudentRepository;
import com.huce.quanlysinhvien.repository.custom.StudentRepositoryCustom;
import com.huce.quanlysinhvien.service.StudentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper mapper;
    private final Response response;
    private final StudentRepositoryCustom studentRepositoryCustom;

    @Override
    public Data getById(Long id) {
        Optional<StudentsEntity> student = studentRepository.findById(id);

        return student.map(data -> response.responseData("Get student successfully", mapper.map(data, StudentsDto.class))).orElseGet(() -> response.responseError("Entity not found"));
    }

    @Override
    public ListData search(StudentSearchRequest request, int page, int pageSize) {
        List<StudentsEntity> students = studentRepositoryCustom.search(request, page, pageSize);
        List<StudentsDto> studentsDto = students.stream().map(p -> this.mapper.map(p, StudentsDto.class)).collect(Collectors.toList());
        int total = studentRepositoryCustom.count(request).intValue();
        int totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        return new ListData(true, "Search student successfully",200, studentsDto, new Pagination(page, pageSize, totalPage, total));
    }

    @Override
    public ListData searchSemesterNull(TypeEnum type, int page, int pageSize) {
        Page<StudentsEntity> students;
        if(Objects.equals(type, TypeEnum.INTERNSHIP)) {
            students = this.studentRepository.searchInternshipNull(PageRequest.of(page, pageSize));
        } else {
            students = this.studentRepository.searchGraduationNull(PageRequest.of(page, pageSize));
        }
        List<StudentsDto> studentsDto = students.stream().map(s -> mapper.map(s, StudentsDto.class)).collect(Collectors.toList());

        return response.responseListData(studentsDto, new Pagination(students.getNumber(), students.getSize(), students.getTotalPages(),
                (int) students.getTotalElements()));
    }

    @Override
    public ListData getListStudentByInternshipId(Long internshipId, int page, int pageSize) {
        Page<StudentsEntity> students = studentRepository.findByInternshipId(internshipId, PageRequest.of(page, pageSize));

        List<StudentsDto> studentsDto = students.stream().map(s -> mapper.map(s, StudentsDto.class)).collect(Collectors.toList());

        return response.responseListData(studentsDto, new Pagination(students.getNumber(), students.getSize(), students.getTotalPages(),
                (int) students.getTotalElements()));
    }

    @Override
    public ListData getListStudentByGraduationId(Long graduationId, int page, int pageSize) {
        Page<StudentsEntity> students = studentRepository.findByGraduationId(graduationId, PageRequest.of(page, pageSize));

        List<StudentsDto> studentsDto = students.stream().map(s -> mapper.map(s, StudentsDto.class)).collect(Collectors.toList());

        return response.responseListData(studentsDto, new Pagination(students.getNumber(), students.getSize(), students.getTotalPages(),
                (int) students.getTotalElements()));
    }

    @Override
    public Data createStudent(StudentsDto studentsDto) {
        Optional<StudentsEntity> student = studentRepository.findByStudentCode(studentsDto.getStudentCode());

        if (student.isPresent()) {
            return response.responseError("Student code does not exist");
        }
        StudentsEntity studentNew = new StudentsEntity(studentsDto);

        return response.responseData("Create successfully", mapper.map(studentRepository.save(studentNew), StudentsDto.class));
    }

    @Override
    public Data updateStudent(StudentsDto studentsDto, Long id) {
        Optional<StudentsEntity> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            return response.responseError("Entity not found");
        }

        StudentsEntity studentUpdate = student.get().mapper(studentsDto);

        return response.responseData("Update successfully", mapper.map(studentRepository.save(studentUpdate), StudentsDto.class));
    }

    @Override
    public Data deleteStudent(Long id) {
        Optional<StudentsEntity> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            return response.responseError("Entity not found");
        }
        student.get().delete();

        return response.responseData("Delete successfully", mapper.map(studentRepository.save(student.get()), StudentsDto.class));
    }
}
