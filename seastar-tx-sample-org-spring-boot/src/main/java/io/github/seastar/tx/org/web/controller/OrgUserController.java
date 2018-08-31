package io.github.seastar.tx.org.web.controller;

import io.github.seastar.tx.org.model.OrgUser;
import io.github.seastar.tx.org.service.OrgUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class OrgUserController {

    @Autowired
    private OrgUserService orgUserService;

    @GetMapping("/{uid}")
    public OrgUser userGet(@PathVariable("uid") Integer uid) {

        OrgUser orgUser = orgUserService.findByUid(uid);

        return orgUser;
    }
}
