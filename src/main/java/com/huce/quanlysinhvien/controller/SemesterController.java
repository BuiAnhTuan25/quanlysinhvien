package com.huce.quanlysinhvien.controller;

import com.huce.quanlysinhvien.constains.SemesterEnum;
import com.huce.quanlysinhvien.constains.StatusEnum;
import com.huce.quanlysinhvien.constains.TypeEnum;
import com.huce.quanlysinhvien.model.dto.SemestersDto;
import com.huce.quanlysinhvien.service.SemesterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/semesters")
public class SemesterController {
    private final SemesterService semesterService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getSemester(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(semesterService.getById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getByTypeAndStatus(
            @RequestParam("type") TypeEnum type,
            @RequestParam("status") SemesterEnum status,
            @RequestParam("page") int page,
            @RequestParam("page-size") int pageSize
            ) {
        return new ResponseEntity<>(semesterService.getByTypeAndStatus(type, status, page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/type")
    public ResponseEntity<?> getByType(
            @RequestParam("type") TypeEnum type,
            @RequestParam("page") int page,
            @RequestParam("page-size") int pageSize
    ) {
        return new ResponseEntity<>(semesterService.getByType(type, page, pageSize), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createSemester(
            @RequestBody SemestersDto semester
    ) throws MessagingException {
        return new ResponseEntity<>(semesterService.createSemester(semester), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSemester(
            @RequestBody SemestersDto semester,
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(semesterService.updateSemester(semester, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSemester(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(semesterService.deleteSemester(id), HttpStatus.OK);
    }
}
