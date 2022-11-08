package com.huce.quanlysinhvien.model.entity;

import com.huce.quanlysinhvien.constains.RoleEnum;
import com.huce.quanlysinhvien.constains.StatusEnum;
import com.huce.quanlysinhvien.model.dto.UsersDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role", unique = true, nullable = false)
    private RoleEnum role;

    @Column(name = "status")
    private StatusEnum status;

    public UsersEntity(UsersDto user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName();
        this.role = user.getRole();
        this.status = user.getStatus();
    }

    public UsersEntity mapper(UsersDto user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName();
        this.role = user.getRole();

        return this;
    }

    public void delete(){
        this.status = StatusEnum.INACTIVE;
    }
}
