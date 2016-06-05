package com.github.sonerik.bugtracktor.adapters.roles;

import android.support.annotation.NonNull;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;

public class RolesAdapter extends FlexibleAdapter<RolesItem> {
    public RolesAdapter(@NonNull List<RolesItem> items) {
        super(items);
    }
}

