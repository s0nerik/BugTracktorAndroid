package com.github.sonerik.bugtracktor.adapters.users;

import android.support.annotation.NonNull;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;

public class UsersAdapter extends FlexibleAdapter<UsersItem> {
    public UsersAdapter(@NonNull List<UsersItem> items) {
        super(items);
    }
}

