package io.github.seastar.tx.sample.service;


import io.github.seastar.tx.sample.mapper.Tx1Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class Tx1Service {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Tx1Mapper tx1Mapper;


    @Autowired
    private Tx2Service tx2Service;


    //    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Transactional
    public void insert1() throws Exception {

        tx1Mapper.insert1();
//        tx2Service.insert1();
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Transactional
    public void selectList() throws Exception {
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("select id, name from tx1");
//

        tx2Service.selectList();
//
//        tx2Service.insert1();

//        return mapList;
    }


    @Transactional
    public void insertTx1D1() throws Exception {

        tx2Service.selectList();
        jdbcTemplate.execute("insert into tx1(id, name) values( 1, 'aa')");
//

    }
}
