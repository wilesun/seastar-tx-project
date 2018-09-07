package io.github.seastar.transaction;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTests {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        System.out.println(atomicInteger.get());

        System.out.println(atomicInteger.incrementAndGet());
        System.out.println(atomicInteger.decrementAndGet());



    }
}
