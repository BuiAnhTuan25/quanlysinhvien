package com.huce.quanlysinhvien.repository;

import com.huce.quanlysinhvien.model.dto.StudentsDto;
import com.huce.quanlysinhvien.model.entity.StudentsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentsEntity, Long> {
    @Query("select s from StudentsEntity s where s.status = 1 and s.name like '%'||:name||'%' and s.studentCode like '%'||:studentCode||'%'")
    Page<StudentsEntity> search(String name, String studentCode, Pageable pageable);

    @Query("select s from StudentsEntity s where s.status = 1 and s.internshipId is null or s.internshipId = ''")
    Page<StudentsEntity> searchInternshipNull(Pageable pageable);

    @Query("select s from StudentsEntity s where s.status = 1 and s.graduationId is null or s.graduationId = ''")
    Page<StudentsEntity> searchGraduationNull(Pageable pageable);

    Page<StudentsEntity> findByGraduationId(@Param("graduationId") Long graduationId, Pageable pageable);

    Page<StudentsEntity> findByInternshipId(@Param("internshipId") Long internshipId, Pageable pageable);

    Optional<StudentsEntity> findByStudentCode(@Param("studentCode") String studentCode);

    @Query("select new com.huce.quanlysinhvien.model.dto.StudentsDto(s.id,s.name,s.studentCode,s.className,s.teacherId,s.internshipPlace,s.graduationTopic,s.internshipId,s.graduationId,e.mark) " +
            " from StudentsEntity s join EvaluationsEntity e on s.id = e.studentId " +
            " where s.graduationId = :graduationId and e.type = 1")
    Page<StudentsDto> statisticGraduation(@Param("graduationId") Long graduationId, Pageable pageable);

    @Query("select new com.huce.quanlysinhvien.model.dto.StudentsDto(s.id,s.name,s.studentCode,s.className,s.teacherId,s.internshipPlace,s.graduationTopic,s.internshipId,s.graduationId,e.mark) " +
            " from StudentsEntity s join EvaluationsEntity e on s.id = e.studentId " +
            " where s.internshipId = :internshipId and e.type = 0")
    Page<StudentsDto> statisticInternship(@Param("internshipId") Long internshipId, Pageable pageable);

    @Query("select s from StudentsEntity s where s.studentCode in :codes")
    List<StudentsEntity> findByCodes(@Param("codes") List<String> codes);
}
