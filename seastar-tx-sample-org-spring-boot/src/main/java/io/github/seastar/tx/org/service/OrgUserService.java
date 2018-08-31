package io.github.seastar.tx.org.service;

import io.github.seastar.tx.ac.model.AcRole;
import io.github.seastar.tx.org.iface.IAcRoleService;
import io.github.seastar.tx.org.model.OrgUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrgUserService {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IAcRoleService acRoleService;

    @Transactional
    public OrgUser findByUid(Integer uid) {

        OrgUser user = jdbcTemplate
                .queryForObject("select uid, name, age from org_user where uid = ?",
                        new BeanPropertyRowMapper<>(OrgUser.class), uid);

        AcRole acRole = acRoleService.findById(1);

        System.out.println(acRole);

        return user;
    }

}
