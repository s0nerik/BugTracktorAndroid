package com.github.sonerik.bugtracktor.utils;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Alex on 5/26/2016.
 */
public class RxBus {
    private static PublishSubject<Object> subject = PublishSubject.create();

    public static <T> Observable<T> on(Class<T> type) {
        return subject.filter(o -> o != null && type.isAssignableFrom(o.getClass())).cast(type);
    }

    public static void publish(Object event) {
        subject.onNext(event);
    }
}