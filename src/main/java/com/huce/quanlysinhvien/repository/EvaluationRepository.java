package com.huce.quanlysinhvien.repository;

import com.huce.quanlysinhvien.constains.TypeEnum;
import com.huce.quanlysinhvien.model.entity.EvaluationsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<EvaluationsEntity, Long>{
    Page<EvaluationsEntity> findByType(@Param("type") TypeEnum type, Pageable pageable);

    Optional<EvaluationsEntity> findByStudentIdAndType(@Param("studentId") Long studentId,@Param("type") TypeEnum type);
}
