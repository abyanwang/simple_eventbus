package com.fuyu.eventbus;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class ObserverRegistry {
    private final Map<Class, CopyOnWriteArraySet<ObserverAction>> registry = new ConcurrentHashMap<>();

    public void registry(Object observer) {
        Map<Class, List<ObserverAction>> observerActions = findAllObserverActions(observer);
        observerActions.forEach((clazz, eventActions) -> {
            CopyOnWriteArraySet<ObserverAction> registeredEventActions = registry
                    .computeIfAbsent(clazz, k-> new CopyOnWriteArraySet<>());
            registeredEventActions.addAll(eventActions);
        });
    }

    public List<ObserverAction> getMatchedObserverActions(Object event) {
        List<ObserverAction> matchedObservers = new ArrayList<>();
        Class postedEventType = event.getClass();
        registry.forEach((key, value) -> {
            System.out.println(key);
            if (postedEventType.isAssignableFrom(key)) {
                matchedObservers.addAll(value);
            }
        });
        return matchedObservers;
    }


    private Map<Class, List<ObserverAction>> findAllObserverActions(Object observer) {
        Map<Class, List<ObserverAction>> observerActions = new HashMap<>();
        Class clazz = observer.getClass();
        getAnnotationMethods(clazz).forEach((method -> {
            Class[] parameters = method.getParameterTypes();
            Class eventType = parameters[0];
            observerActions.computeIfAbsent(eventType, k->new ArrayList<>())
                    .add(new ObserverAction(observer, method));
        }));
        return observerActions;
    }

    private List<Method> getAnnotationMethods(Class clazz) {
        List<Method> methodList = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Subscribe.class)) {
                methodList.add(method);
            }
        }
        return methodList;
    }
}
