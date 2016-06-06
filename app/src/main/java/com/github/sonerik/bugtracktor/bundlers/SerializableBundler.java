package com.github.sonerik.bugtracktor.bundlers;

import android.os.Bundle;

import java.io.Serializable;

import icepick.Bundler;

/**
 * Created by Alex on 6/3/2016.
 */
public class SerializableBundler implements Bundler<Object> {
    @Override
    public void put(String key, Object value, Bundle bundle) {
        bundle.putSerializable(key, (Serializable) value);
    }

    @Override
    public Object get(String key, Bundle bundle) {
        return bundle.getSerializable(key);
    }
}