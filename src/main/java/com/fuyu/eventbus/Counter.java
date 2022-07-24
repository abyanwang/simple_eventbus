package com.fuyu.eventbus;

public class Counter {
    private int cnt = 0;

    @Subscribe
    public void plus(String event) {
        cnt++;
        System.out.println("plus");
    }
}
