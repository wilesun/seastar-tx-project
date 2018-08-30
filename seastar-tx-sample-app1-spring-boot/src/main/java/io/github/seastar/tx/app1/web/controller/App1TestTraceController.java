package io.github.seastar.tx.app1.web.controller;

import io.github.seastar.tx.app1.service.App1Tx1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test/trace")
@RestController
public class App1TestTraceController {


    @Autowired
    private App1Tx1Service app1Tx1Service;

    @RequestMapping("/insert")
    public String insertData() throws Exception {

        app1Tx1Service.testTx1Insert1();

        return "";
    }


    @RequestMapping("/hi")
    public String hiGet() throws Exception {

        app1Tx1Service.testTx1Select1();

        return "h1";
    }

}
