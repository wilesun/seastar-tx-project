package com.kqgeo.seastar.tx.sample.service;

import com.kqgeo.seastar.tx.sample.mapper.Tx1Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class Tx3Service {

    @Autowired
    private Tx1Mapper tx1Mapper;

    @Autowired
    private Tx1Service tx1Service;

    @Autowired
    private Tx2Service tx2Service;

    @Autowired
    private RestTemplate restTemplate;


    @Transactional
    public void selectList() throws Exception {

        tx1Service.selectList();
//        tx1Mapper.select1();
        String s = restTemplate.getForObject("http://192.168.0.189:9831/tx-app1/test/trace/hi", String.class);
    }


    @Transactional
    public void insert1(String s) throws Exception {

        tx1Service.insert1();

        restTemplate.getForObject("http://192.168.0.189:9831/tx-app1/test/trace/insert", String.class);


//        if ("s".equals(s)) {
//            throw new RuntimeException("ss");
//        }
    }


    @Transactional
    public void insert2() throws Exception {
        tx1Service.insert1();
        tx2Service.insert2();

        String s = restTemplate.getForObject("http://192.168.0.189:9831/tx-app1/test/trace/hi", String.class);
        String s2 = restTemplate.getForObject("http://192.168.0.189:9831/tx-app1/test/trace/hi", String.class);

    }
}
