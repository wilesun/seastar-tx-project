package io.github.seastar.tx.org;

import java.util.Objects;

public class HashCodeTests {

    public static void main(String[] args) {
        System.out.printf("hash [Aa]: %d, hash [BB]: %d \n", Objects.hash("Aa"), Objects.hash("BB"));
        System.out.printf("hash [柳志崇]: %d, hash [柳山왡]: %d \n", Objects.hash("柳志崇"), Objects.hash("柳山왡"));
    }
}
