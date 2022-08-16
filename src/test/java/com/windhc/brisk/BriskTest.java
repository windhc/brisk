package com.windhc.brisk;

import java.util.List;

public class BriskTest {

    public static void main(String[] args) {
        Brisk brisk = new Brisk();
        brisk.addInterceptor(new TestInterceptor(), List.of("/**"));
        brisk.start(8888, "com.windhc.brisk");
    }
}
