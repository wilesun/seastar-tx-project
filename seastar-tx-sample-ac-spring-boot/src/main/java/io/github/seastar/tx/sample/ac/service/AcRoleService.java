package io.github.seastar.tx.sample.ac.service;

import io.github.seastar.tx.ac.model.AcRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AcRoleService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public AcRole findById(Integer id) {
        AcRole acRole = jdbcTemplate.
                queryForObject("select id, name from ac_role where id=?",
                        new BeanPropertyRowMapper<>(AcRole.class), id);
        return acRole;
    }

}
