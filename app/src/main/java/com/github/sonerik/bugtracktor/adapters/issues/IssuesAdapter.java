package com.github.sonerik.bugtracktor.adapters.issues;

import android.support.annotation.NonNull;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;

public class IssuesAdapter extends FlexibleAdapter<IssuesItem> {
    public IssuesAdapter(@NonNull List<IssuesItem> items) {
        super(items);
    }
}

