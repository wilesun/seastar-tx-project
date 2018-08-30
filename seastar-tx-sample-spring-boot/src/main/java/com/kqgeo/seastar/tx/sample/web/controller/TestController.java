package com.kqgeo.seastar.tx.sample.web.controller;

import com.kqgeo.seastar.tx.sample.service.Tx3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private Tx3Service tx3Service;

    @RequestMapping("/v1")
    public String testV1() throws Exception {

//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//
//        AtomicInteger atomicInteger = new AtomicInteger();
//
//        for (int i = 0; i < 40; i++) {
//            executorService.submit(new Runnable() {
//                @Override
//                public void run() {
//                    for (int j = 0; j < 20; j++) {
//
//                        long s = System.currentTimeMillis();
//
//                        try {
//                            tx3Service.insert2();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                        long e = System.currentTimeMillis();
//
//                        System.out.println((e - s) + "ms");
//                    }
//                }
//            });
//
//        }

        long s = System.currentTimeMillis();
        tx3Service.selectList();
        long e = System.currentTimeMillis();
        System.out.println((e - s) + "ms");
        return "";
    }

    @RequestMapping("/insert")
    public String insertV1(@RequestParam(required = false) String s2) throws Exception {

        long s = System.currentTimeMillis();
        tx3Service.insert1(s2);
        long e = System.currentTimeMillis();
        System.out.println((e - s) + "ms");
        return "";
    }


}
