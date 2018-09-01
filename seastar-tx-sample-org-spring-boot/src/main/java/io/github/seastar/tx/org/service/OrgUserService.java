package io.github.seastar.tx.org.service;

import io.github.seastar.tx.ac.model.AcRole;
import io.github.seastar.tx.org.iface.IAcRoleService;
import io.github.seastar.tx.org.model.OrgDepartment;
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

    @Autowired
    private OrgDepartmentService orgDepartmentService;

    @Transactional
    public OrgUser findByUid(Integer uid) {

        OrgUser user = jdbcTemplate
                .queryForObject("select uid, name, age, dept_id from org_user where uid = ?",
                        new BeanPropertyRowMapper<>(OrgUser.class), uid);


        orgDepartmentService.findById(user.getDeptId());
        OrgDepartment department = orgDepartmentService.findById(user.getDeptId());

        user.setDepartment(department);

        AcRole acRole = acRoleService.findById(1);
        System.out.println(acRole);

        return user;
    }

}
