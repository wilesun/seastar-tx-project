package io.github.seastar.tx.org.model;

import lombok.Data;

@Data
public class OrgUser {

    private Integer uid;

    private String name;

    private int age = 0;

    private Integer deptId;

    private OrgDepartment department;

}
