package com.huce.quanlysinhvien.controller;

import com.huce.quanlysinhvien.model.dto.StudentsDto;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(studentService.getById(id), HttpStatus.OK);
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
