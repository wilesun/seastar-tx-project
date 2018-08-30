package com.kqgeo.seastar.transaction;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTests {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        System.out.println(atomicInteger.get());

        System.out.println(atomicInteger.incrementAndGet());
        System.out.println(atomicInteger.decrementAndGet());
    }
}
