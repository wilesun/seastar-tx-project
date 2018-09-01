package io.github.seastar.tx.sample.ac.service;

import io.github.seastar.tx.ac.model.AcPermission;
import io.github.seastar.tx.ac.model.AcRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class AcRoleService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AcPermissionService acPermissionService;

    @Transactional
    public AcRole findById(Integer id) {
        AcRole acRole = jdbcTemplate.
                queryForObject("select id, name from ac_role where id=?",
                        new BeanPropertyRowMapper<>(AcRole.class), id);

        if (!ObjectUtils.isEmpty(acRole)) {

            List<AcPermission> acPermissions = acPermissionService.findListByRoleId(acRole.getId());

            System.out.println(acPermissions);
        }

        return acRole;
    }

}
