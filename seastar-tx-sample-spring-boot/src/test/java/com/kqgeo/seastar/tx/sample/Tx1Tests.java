package com.kqgeo.seastar.tx.sample;

import com.kqgeo.seastar.tx.sample.service.Tx1Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Tx1Tests {

    @Autowired
    private Tx1Service test1Service;


    @Test
    public void testSelect() throws Exception {

        test1Service.selectList();

//        TimeUnit.MINUTES.sleep(10);

//        Thread.sleep(200000);

    }

    @Test
    public void testInsertTx1D1() throws Exception {

        test1Service.insertTx1D1();

//        TimeUnit.MINUTES.sleep(10);

//        Thread.sleep(200000);

    }
}
