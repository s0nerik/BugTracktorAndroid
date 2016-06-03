package com.github.sonerik.bugtracktor.bundlers;

import android.os.Bundle;

import org.parceler.Parcels;

import icepick.Bundler;

/**
 * Created by Alex on 6/3/2016.
 */
public class ParcelBundler<T> implements Bundler<T> {
    public void put(String key, T value, Bundle bundle) {
        bundle.putParcelable(key, Parcels.wrap(value));
    }

    public T get(String key, Bundle bundle) {
        return Parcels.unwrap(bundle.getParcelable(key));
    }
}