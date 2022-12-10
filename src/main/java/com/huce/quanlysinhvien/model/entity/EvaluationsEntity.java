package com.huce.quanlysinhvien.model.entity;


import com.huce.quanlysinhvien.constains.SemesterEnum;
import com.huce.quanlysinhvien.constains.StatusEnum;
import com.huce.quanlysinhvien.constains.TypeEnum;
import com.huce.quanlysinhvien.model.dto.EvaluationsDto;
import com.huce.quanlysinhvien.model.dto.SemestersDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "evaluations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvaluationsEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "topic")
    private String topic;

    @Column(name = "mark")
    private String mark;

    @Column(name = "note")
    private String note;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "type")
    private TypeEnum type;

    @Column(name = "status")
    private StatusEnum status;

    public EvaluationsEntity(EvaluationsDto evaluation) {
        this.topic = evaluation.getTopic();
        this.mark = evaluation.getMark();
        this.note = evaluation.getNote();
        this.studentId = evaluation.getStudentId();
        this.type = evaluation.getType();
        this.status = StatusEnum.ACTIVE;
    }

    public EvaluationsEntity mapper(EvaluationsDto evaluation) {
        this.topic = evaluation.getTopic();
        this.mark = evaluation.getMark();
        this.note = evaluation.getNote();
        this.studentId = evaluation.getStudentId();
        this.type = evaluation.getType();

        return this;
    }

    public void delete(){
        this.status = StatusEnum.INACTIVE;
    }
}
