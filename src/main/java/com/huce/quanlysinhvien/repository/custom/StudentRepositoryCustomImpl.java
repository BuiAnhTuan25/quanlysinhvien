package com.huce.quanlysinhvien.repository.custom;

import com.huce.quanlysinhvien.model.entity.StudentsEntity;
import com.huce.quanlysinhvien.model.request.StudentSearchRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class StudentRepositoryCustomImpl implements StudentRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<StudentsEntity> search(StudentSearchRequest request, int page, int pageSize) {

        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT s FROM StudentsEntity s ");
        sql.append(createWhereQuery(request, values));
        sql.append(createOrderQuery(request));
        Query query = entityManager.createQuery(sql.toString(), StudentsEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    StringBuilder createWhereQuery(StudentSearchRequest request, Map<String, Object> values) {
        StringBuilder sql = new StringBuilder();
        sql.append(" WHERE s.status = 1 ");
        if (Objects.nonNull(request.getName())) {
            sql.append(" and s.name like '%'||:name||'%' ");
            values.put("name", request.getName());
        }
        if (Objects.nonNull(request.getStudentCode())) {
            sql.append(" and s.studentCode like '%'||:studentCode||'%' ");
            values.put("studentCode", request.getStudentCode());
        }
        if (Objects.nonNull(request.getGraduationId())) {
            sql.append(" and s.graduationId = :graduationId ");
            values.put("graduationId", request.getGraduationId());
        }
        if (Objects.nonNull(request.getInternshipId())) {
            sql.append(" and s.internshipId = :internshipId ");
            values.put("internshipId", request.getInternshipId());
        }
        if (Objects.nonNull(request.getTeacherId())) {
            sql.append(" and s.teacherId = :teacherId ");
            values.put("teacherId", request.getTeacherId());
        }
        return sql;
    }

    StringBuilder createOrderQuery(StudentSearchRequest request) {
        StringBuilder sql = new StringBuilder(" ");
        if (StringUtils.hasLength(request.getSortBy())) {
            sql.append(" order by s.").append(request.getSortBy().replace(".", " "));
        }
        return sql;
    }

    @Override
    public Long count(StudentSearchRequest request) {
        try {
            Map<String, Object> values = new HashMap<>();
            String sql = "SELECT COUNT(s) FROM StudentsEntity s " +
                    this.createWhereQuery(request, values);
            Query query = this.entityManager.createQuery(sql);
            values.forEach(query::setParameter);
            return (Long) query.getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return 0L;
    }
}
