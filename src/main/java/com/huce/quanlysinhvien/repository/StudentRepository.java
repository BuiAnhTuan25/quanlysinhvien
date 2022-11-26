package com.huce.quanlysinhvien.repository;

import com.huce.quanlysinhvien.model.entity.StudentsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentsEntity, Long> {
    Page<StudentsEntity> findByGraduationId(@Param("graduationId") Long graduationId, Pageable pageable);
    Page<StudentsEntity> findByInternshipId(@Param("internshipId") Long internshipId, Pageable pageable);
    Optional<StudentsEntity> findByStudentCode(@Param("studentCode") String studentCode);
}
