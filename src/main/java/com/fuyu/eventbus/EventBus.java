package com.fuyu.eventbus;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EventBus {
    private Executor executor = Executors.newCachedThreadPool();

    private ObserverRegistry observerRegistry = new ObserverRegistry();

    public void registry(Object object) {
        observerRegistry.registry(object);
    }

    public void post(Object event) {
        List<ObserverAction> observerActions = observerRegistry.getMatchedObserverActions(event);
        observerActions.forEach(observerAction -> executor.execute(()-> observerAction.execute(event)));
    }
}
