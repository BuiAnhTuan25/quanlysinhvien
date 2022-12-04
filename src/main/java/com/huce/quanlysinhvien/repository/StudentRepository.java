package com.huce.quanlysinhvien.repository;

import com.huce.quanlysinhvien.model.entity.StudentsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentsEntity, Long> {
    @Query("select s from StudentsEntity s where s.status = 1 and s.name like '%'||:name||'%' and s.studentCode like '%'||:studentCode||'%'")
    Page<StudentsEntity> search(String name, String studentCode, Pageable pageable);
    @Query("select s from StudentsEntity s where s.status = 1 and s.internshipId is null")
    Page<StudentsEntity> searchInternshipNull(Pageable pageable);
    @Query("select s from StudentsEntity s where s.status = 1 and s.graduationId is null")
    Page<StudentsEntity> searchGraduationNull(Pageable pageable);
    Page<StudentsEntity> findByGraduationId(@Param("graduationId") Long graduationId, Pageable pageable);
    Page<StudentsEntity> findByInternshipId(@Param("internshipId") Long internshipId, Pageable pageable);
    Optional<StudentsEntity> findByStudentCode(@Param("studentCode") String studentCode);
}
