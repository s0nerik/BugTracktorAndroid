package com.github.sonerik.bugtracktor.adapters.users;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.models.User;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(of = "user")
@RequiredArgsConstructor
public class UsersItem extends AbstractFlexibleItem<UsersViewHolder> {

    private final User user;
    private final boolean editMode;

    @Override
    public int getLayoutRes() {
        return R.layout.item_users;
    }

    @Override
    public UsersViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new UsersViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, UsersViewHolder holder, int position, List payloads) {
        holder.setUser(user);
        holder.setEditMode(editMode);
    }

}

