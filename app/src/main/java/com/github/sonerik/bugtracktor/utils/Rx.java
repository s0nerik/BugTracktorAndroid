package com.github.sonerik.bugtracktor.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sonerik on 5/28/16.
 */
public class Rx {
    public static  <T> Observable.Transformer<T, T> applySchedulers() {
        return obs -> obs.subscribeOn(Schedulers.io())
                         .observeOn(AndroidSchedulers.mainThread());
    }
}
