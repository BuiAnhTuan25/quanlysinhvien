package com.huce.quanlysinhvien.repository.custom;

import com.huce.quanlysinhvien.model.entity.SemestersEntity;
import com.huce.quanlysinhvien.model.request.SemesterSearchRequest;
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

@AllArgsConstructor
@Repository
public class SemesterRepositoryCustomImpl implements SemesterRepositoryCustom{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SemestersEntity> search(SemesterSearchRequest request, int page, int pageSize) {

        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT s FROM SemestersEntity s ");
        sql.append(createWhereQuery(request, values));
        sql.append(createOrderQuery(request));
        Query query = entityManager.createQuery(sql.toString(), SemestersEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    StringBuilder createWhereQuery(SemesterSearchRequest request, Map<String, Object> values) {
        StringBuilder sql = new StringBuilder();
        sql.append(" where s.id like '%%' ");
        if (Objects.nonNull(request.getYear())) {
            sql.append(" and s.year like '%'||:year||'%' ");
            values.put("year", request.getYear());
        }
        if (Objects.nonNull(request.getSemester())) {
            sql.append(" and s.semester like '%'||:semester||'%' ");
            values.put("semester", request.getSemester());
        }
        if (Objects.nonNull(request.getStatus())) {
            sql.append(" and s.status = :status ");
            values.put("status", request.getStatus());
        }
        if (Objects.nonNull(request.getType())) {
            sql.append(" and s.type = :type ");
            values.put("type", request.getType());
        }
        return sql;
    }

    StringBuilder createOrderQuery(SemesterSearchRequest request) {
        StringBuilder sql = new StringBuilder(" ");
        if (StringUtils.hasLength(request.getSortBy())) {
            sql.append(" order by s.").append(request.getSortBy().replace(".", " "));
        }
        return sql;
    }

    @Override
    public Long count(SemesterSearchRequest request) {
        try {
            Map<String, Object> values = new HashMap<>();
            String sql = "SELECT COUNT(s) FROM SemestersEntity s " +
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
