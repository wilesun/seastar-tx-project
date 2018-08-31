package io.github.seastar.tx.org.service;

import io.github.seastar.tx.org.model.OrgUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrgUserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public OrgUser findByUid(Integer uid) {

        OrgUser user = jdbcTemplate
                .queryForObject("select uid, name, age from org_user where uid = ?",
                        new BeanPropertyRowMapper<>(OrgUser.class), uid);

        return user;
    }

}
