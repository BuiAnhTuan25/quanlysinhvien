package com.huce.quanlysinhvien.model.entity;

import com.huce.quanlysinhvien.constains.SemesterEnum;
import com.huce.quanlysinhvien.constains.TypeEnum;
import com.huce.quanlysinhvien.model.dto.SemestersDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "semesters")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SemestersEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "semester")
    private String semester;

    @Column(name = "year")
    private String year;

    @Column(name = "type")
    private TypeEnum type;

    @Column(name = "status")
    private SemesterEnum status;

    public SemestersEntity(SemestersDto semester) {
        this.semester = semester.getSemester();
        this.year = semester.getYear();
        this.type = semester.getType();
        this.status = semester.getStatus();
    }

    public SemestersEntity mapper(SemestersDto semester) {
        this.semester = semester.getSemester();
        this.year = semester.getYear();
        this.type = semester.getType();

        return this;
    }

    public void delete(){
        this.status = SemesterEnum.CLOSE;
    }
}

