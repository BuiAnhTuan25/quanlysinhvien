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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
        return new ListData(true, "Search student successfully", 200, studentsDto, new Pagination(page, pageSize, totalPage, total));
    }

    @Override
    public ListData searchSemesterNull(TypeEnum type, int page, int pageSize) {
        Page<StudentsEntity> students;
        if (Objects.equals(type, TypeEnum.INTERNSHIP)) {
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
    public ListData statisticInternship(Long internshipId, int page, int pageSize) {
        Page<StudentsDto> students = studentRepository.statisticInternship(internshipId, PageRequest.of(page, pageSize));

        return response.responseListData(students.getContent(), new Pagination(students.getNumber(), students.getSize(), students.getTotalPages(),
                (int) students.getTotalElements()));
    }

    @Override
    public ListData statisticGraduation(Long graduationId, int page, int pageSize) {
        Page<StudentsDto> students = studentRepository.statisticGraduation(graduationId, PageRequest.of(page, pageSize));

        return response.responseListData(students.getContent(), new Pagination(students.getNumber(), students.getSize(), students.getTotalPages(),
                (int) students.getTotalElements()));
    }

    @Override
    @Transactional
    public Data createStudent(StudentsDto studentsDto) {
        Optional<StudentsEntity> student = studentRepository.findByStudentCode(studentsDto.getStudentCode());

        if (student.isPresent()) {
            return response.responseError("Student code does not exist");
        }
        StudentsEntity studentNew = new StudentsEntity(studentsDto);

        return response.responseData("Create successfully", mapper.map(studentRepository.save(studentNew), StudentsDto.class));
    }

    @Override
    @Transactional
    public Data createListStudent(List<StudentsDto> students) {
        List<String> codes = students.stream().map(StudentsDto::getStudentCode).collect(Collectors.toList());
        List<StudentsEntity> studentsExist = studentRepository.findByCodes(codes);

        if (!CollectionUtils.isEmpty(studentsExist)) {
            return response.responseError("Student code does not exist");
        }
        List<StudentsEntity> studentsEntities = new ArrayList<>();
        students.forEach(s -> {
            studentsEntities.add(new StudentsEntity(s));
        });
        List<StudentsDto> studentsDtos = this.studentRepository.saveAll(studentsEntities).stream().map(s -> this.mapper.map(s, StudentsDto.class)).collect(Collectors.toList());

        return response.responseData("Create successfully", studentsDtos);
    }

    @Override
    @Transactional
    public Data updateStudent(StudentsDto studentsDto, Long id) {
        Optional<StudentsEntity> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            return response.responseError("Entity not found");
        }

        StudentsEntity studentUpdate = student.get().mapper(studentsDto);

        return response.responseData("Update successfully", mapper.map(studentRepository.save(studentUpdate), StudentsDto.class));
    }

    @Override
    @Transactional
    public Data deleteStudent(Long id) {
        Optional<StudentsEntity> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            return response.responseError("Entity not found");
        }
        student.get().delete();

        return response.responseData("Delete successfully", mapper.map(studentRepository.save(student.get()), StudentsDto.class));
    }

    @Override
    @Transactional
    public Data deleteAll() {
        List<StudentsEntity> students = this.studentRepository.findAll();
        students.forEach(StudentsEntity::delete);
        this.studentRepository.saveAll(students);

        return response.responseData("Delete successfully", null);
    }
}
