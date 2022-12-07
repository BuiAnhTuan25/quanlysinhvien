package com.huce.quanlysinhvien.repository.custom;

import com.huce.quanlysinhvien.model.entity.UsersEntity;
import com.huce.quanlysinhvien.model.request.UserSearchRequest;
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
public class UserRepositoryCustomImpl implements UserRepositoryCustom{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UsersEntity> search(UserSearchRequest request, int page, int pageSize) {

        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u FROM UsersEntity u ");
        sql.append(createWhereQuery(request, values));
        sql.append(createOrderQuery(request));
        Query query = entityManager.createQuery(sql.toString(), UsersEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    StringBuilder createWhereQuery(UserSearchRequest request, Map<String, Object> values) {
        StringBuilder sql = new StringBuilder();
        sql.append(" WHERE u.status = 1 ");
        if (Objects.nonNull(request.getName())) {
            sql.append(" and u.name like '%'||:name||'%' ");
            values.put("name", request.getName());
        }
        if (Objects.nonNull(request.getUsername())) {
            sql.append(" and u.username like '%'||:username||'%' ");
            values.put("username", request.getUsername());
        }
        if (Objects.nonNull(request.getRole())) {
            sql.append(" and u.role like '%'||:role||'%' ");
            values.put("role", request.getRole().ordinal());
        }

        return sql;
    }

    StringBuilder createOrderQuery(UserSearchRequest request) {
        StringBuilder sql = new StringBuilder(" ");
        if (StringUtils.hasLength(request.getSortBy())) {
            sql.append(" order by u.").append(request.getSortBy().replace(".", " "));
        }
        return sql;
    }

    @Override
    public Long count(UserSearchRequest request) {
        try {
            Map<String, Object> values = new HashMap<>();
            String sql = "SELECT COUNT(u) FROM UsersEntity u " +
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
