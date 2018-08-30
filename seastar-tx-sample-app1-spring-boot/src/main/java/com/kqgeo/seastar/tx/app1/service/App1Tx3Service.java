package com.kqgeo.seastar.tx.app1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class App1Tx3Service {

    @Autowired
    private App1Tx2Service app1Tx2Service;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Map<String, Object>> selectList() {

        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("select id, name from app1_tx3");
//        app1Tx2Service.selectList2();
//        int i = 1 / 0;
        return mapList;
    }


}
