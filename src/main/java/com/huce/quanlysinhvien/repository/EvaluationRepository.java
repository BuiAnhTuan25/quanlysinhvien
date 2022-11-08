package com.huce.quanlysinhvien.repository;

import com.huce.quanlysinhvien.constains.TypeEnum;
import com.huce.quanlysinhvien.model.entity.EvaluationsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<EvaluationsEntity, Long>{
    Page<EvaluationsEntity> findByType(TypeEnum type, Pageable pageable);
}
