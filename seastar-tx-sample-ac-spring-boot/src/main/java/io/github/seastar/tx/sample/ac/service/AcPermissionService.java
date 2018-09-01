package io.github.seastar.tx.sample.ac.service;

import io.github.seastar.tx.ac.model.AcPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AcPermissionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public List<AcPermission> findListByRoleId(Integer roleId) {


        String sql = "select id, code, name, rid as roleId from ac_permission where rid = ?";


        List<AcPermission> permissions =
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AcPermission.class), roleId);

        return permissions;

    }
}
