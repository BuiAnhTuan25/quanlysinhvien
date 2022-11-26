package com.huce.quanlysinhvien.model.entity;

import com.huce.quanlysinhvien.constains.StatusEnum;
import com.huce.quanlysinhvien.model.dto.StudentsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "students")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentsEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "student_code")
    private String studentCode;

    @Column(name = "class_name")
    private String className;

    @Column(name = "teacher_id")
    private Long teacherId;

    @Column(name = "internship_place")
    private String internshipPlace;

    @Column(name = "graduation_topic")
    private String graduationTopic;

    @Column(name = "internship_id")
    private Long internshipId;

    @Column(name = "graduation_id")
    private Long graduationId;

    @Column(name = "status")
    private StatusEnum status;

    public StudentsEntity(StudentsDto student) {
        this.name = student.getName();
        this.studentCode = student.getStudentCode();
        this.className = student.getClassName();
        this.teacherId = student.getTeacherId();
        this.internshipPlace = student.getInternshipPlace();
        this.graduationTopic = student.getGraduationTopic();
        this.graduationId = student.getGraduationId();
        this.internshipId = student.getInternshipId();
        this.status = student.getStatus();
    }

    public StudentsEntity mapper(StudentsDto student) {
        this.name = student.getName();
        this.studentCode = student.getStudentCode();
        this.className = student.getClassName();
        this.teacherId = student.getTeacherId();
        this.internshipPlace = student.getInternshipPlace();
        this.graduationTopic = student.getGraduationTopic();
        this.graduationId = student.getGraduationId();
        this.internshipId = student.getInternshipId();

        return this;
    }

    public void delete(){
        this.status = StatusEnum.INACTIVE;
    }
}
