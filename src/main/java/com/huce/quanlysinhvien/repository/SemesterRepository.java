package com.huce.quanlysinhvien.repository;

import com.huce.quanlysinhvien.constains.SemesterEnum;
import com.huce.quanlysinhvien.constains.TypeEnum;
import com.huce.quanlysinhvien.model.entity.SemestersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends JpaRepository<SemestersEntity, Long> {
    Page<SemestersEntity> findByTypeAndStatus(TypeEnum type, SemesterEnum status , Pageable pageable);
    Page<SemestersEntity> findByType(TypeEnum type, Pageable pageable);
}
