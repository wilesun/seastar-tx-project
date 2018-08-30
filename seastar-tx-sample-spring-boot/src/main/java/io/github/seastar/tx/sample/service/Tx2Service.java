package io.github.seastar.tx.sample.service;

import io.github.seastar.tx.sample.mapper.Tx2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class Tx2Service {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private Tx2Mapper tx2Mapper;

    //    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Transactional
//    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void selectList() {


        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("select id, name from tx1");
//
//
//        System.out.println(mapList);
    }


    //    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Transactional
//    @Transactional
    public void insert1() throws Exception {

        tx2Mapper.insert1();

//        throw new RuntimeException("roll 错误");
    }

    @Transactional
//    @Transactional
    public void insert2() throws Exception {

//        String s = restTemplate.getForObject("http://192.168.0.189:9831/tx-app1/test/trace/hi", String.class);
//        System.out.println(s);

//        throw new RuntimeException("roll 错误");
    }
}
