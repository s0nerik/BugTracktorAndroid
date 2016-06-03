package com.github.sonerik.bugtracktor.bundlers;

import android.os.Bundle;

import com.github.sonerik.bugtracktor.models.Issue;

import org.parceler.Parcels;

import icepick.Bundler;

/**
 * Created by Alex on 6/3/2016.
 */
public class IssueBundler implements Bundler<Issue> {
    public void put(String key, Issue value, Bundle bundle) {
        bundle.putParcelable(key, Parcels.wrap(value));
    }

    public Issue get(String key, Bundle bundle) {
        return Parcels.unwrap(bundle.getParcelable(key));
    }
}