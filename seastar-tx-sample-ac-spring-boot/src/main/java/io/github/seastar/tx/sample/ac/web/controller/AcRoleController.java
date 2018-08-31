package io.github.seastar.tx.sample.ac.web.controller;

import io.github.seastar.tx.ac.model.AcRole;
import io.github.seastar.tx.sample.ac.service.AcRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class AcRoleController {

    @Autowired
    private AcRoleService acRoleService;


    @GetMapping("/{roleId}")
    public AcRole roleGet(@PathVariable("roleId") Integer roleId) {
        return acRoleService.findById(roleId);
    }
}
