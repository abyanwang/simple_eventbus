package com.fuyu.eventbus;

import java.lang.reflect.Method;

public class ObserverAction {
    private Object target;

    private Method method;

    public ObserverAction(Object object, Method method) {
        this.target = object;
        this.method = method;
        this.method.setAccessible(true);
    }

    public void execute(Object event) {
        try {
            this.method.invoke(target, event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
