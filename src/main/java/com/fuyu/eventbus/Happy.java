package com.fuyu.eventbus;

public class Happy {
    private int cnt = 0;

    @Subscribe
    public void happy(String event) {
        cnt++;
        System.out.println("happy");
    }
}
