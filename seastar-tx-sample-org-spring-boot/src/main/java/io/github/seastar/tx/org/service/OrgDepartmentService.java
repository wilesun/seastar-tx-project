package io.github.seastar.tx.org.service;

import io.github.seastar.tx.org.model.OrgDepartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrgDepartmentService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public OrgDepartment findById(Integer deptId) {
        return jdbcTemplate.queryForObject("select id, name from org_department where id = ?",
                new BeanPropertyRowMapper<>(OrgDepartment.class), deptId);
    }
}
