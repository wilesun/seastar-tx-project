package io.github.seastar.tx.app1.service;

import io.github.seastar.tx.app1.mapper.App1Tx2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class App1Tx2Service {

    @Autowired
    private App1Tx2Mapper app1Tx2Mapper;

    @Transactional
    public void testTx2Select1() {
        app1Tx2Mapper.select1();
    }

}
