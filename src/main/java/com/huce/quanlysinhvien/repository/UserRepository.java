package com.huce.quanlysinhvien.repository;

import com.huce.quanlysinhvien.constains.StatusEnum;
import com.huce.quanlysinhvien.model.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Long> {
    Optional<UsersEntity> findByIdAndStatus(@Param("id") Long id, @Param("status") StatusEnum status);

    Optional<UsersEntity> findByUsername(@Param("username") String username);

    @Query("SELECT u FROM UsersEntity u WHERE u.username = :username")
    UsersEntity getUserByUsername(@Param("username") String username);
}
