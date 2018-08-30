package com.kqgeo.seastar.tx.app1.service;

import com.kqgeo.seastar.tx.app1.mapper.App1Tx1Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class App1Tx1Service {


    @Autowired
    private App1Tx1Mapper tx1Mapper;

    @Autowired
    private App1Tx2Service app1Tx2Service;


    @Transactional
    public void testTx1Select1() {
        tx1Mapper.select1();

//        app1Tx2Service.testTx2Select1();
    }

    @Transactional
    public void testTx1Insert1() {
        tx1Mapper.insert1();

//        int i = 1 / 0;
//        app1Tx2Service.testTx2Select1();
    }

}
