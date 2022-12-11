package com.huce.quanlysinhvien.controller;

import com.huce.quanlysinhvien.constains.TypeEnum;
import com.huce.quanlysinhvien.model.dto.StudentsDto;
import com.huce.quanlysinhvien.model.request.StudentSearchRequest;
import com.huce.quanlysinhvien.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/internship/{id}")
    public ResponseEntity<?> getByInternshipId(
            @PathVariable Long id,
            @RequestParam("page") int page,
            @RequestParam("page-size") int pageSize
    ) {
        return new ResponseEntity<>(studentService.getListStudentByInternshipId(id, page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/graduation/{id}")
    public ResponseEntity<?> getByGraduationId(
            @PathVariable Long id,
            @RequestParam("page") int page,
            @RequestParam("page-size") int pageSize
    ) {
        return new ResponseEntity<>(studentService.getListStudentByGraduationId(id, page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/statistic-internship/{id}")
    public ResponseEntity<?> statisticInternship(
            @PathVariable Long id,
            @RequestParam("page") int page,
            @RequestParam("page-size") int pageSize
    ) {
        return new ResponseEntity<>(studentService.statisticInternship(id, page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/statistic-graduation/{id}")
    public ResponseEntity<?> statisticGraduation(
            @PathVariable Long id,
            @RequestParam("page") int page,
            @RequestParam("page-size") int pageSize
    ) {
        return new ResponseEntity<>(studentService.statisticGraduation(id, page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/add-semester")
    public ResponseEntity<?> getBySemesterNull(
            @RequestParam("type") TypeEnum type,
            @RequestParam("page") int page,
            @RequestParam("page-size") int pageSize
    ) {
        return new ResponseEntity<>(studentService.searchSemesterNull(type, page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(studentService.getById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> search(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "student-code", required = false) String studentCode,
            @RequestParam(value = "graduation-id", required = false) Long graduationId,
            @RequestParam(value = "internship-id", required = false) Long internshipId,
            @RequestParam(value = "teacher-id", required = false) Long teacherId,
            @RequestParam(value = "sort-by", required = false) String sortBy,
            @RequestParam("page") int page,
            @RequestParam("page-size") int pageSize
            ) {
        return new ResponseEntity<>(studentService.search(new StudentSearchRequest(name, studentCode, graduationId, internshipId, teacherId, sortBy), page, pageSize), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createStudent(
            @RequestBody StudentsDto student
    ) throws MessagingException {
        return new ResponseEntity<>(studentService.createStudent(student), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(
            @RequestBody StudentsDto student,
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(studentService.updateStudent(student, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(studentService.deleteStudent(id), HttpStatus.OK);
    }
}
