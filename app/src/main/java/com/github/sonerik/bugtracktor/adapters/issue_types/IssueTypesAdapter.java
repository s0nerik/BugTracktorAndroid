package com.github.sonerik.bugtracktor.adapters.issue_types;

import android.support.annotation.NonNull;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;

public class IssueTypesAdapter extends FlexibleAdapter<IssueTypesItem> {
    public IssueTypesAdapter(@NonNull List<IssueTypesItem> items) {
        super(items);
    }
}

