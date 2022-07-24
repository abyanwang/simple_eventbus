package com.fuyu.eventbus;

import org.junit.Test;


public class EventBusTest {
    @Test
    public void test() {
        EventBus eventBus = new EventBus();
        Counter counter = new Counter();
        Happy happy = new Happy();
        Happy happy1 = new Happy();
        eventBus.registry(counter);
        eventBus.registry(happy);
        eventBus.registry(happy1);

        eventBus.post("hello world");
    }
}
