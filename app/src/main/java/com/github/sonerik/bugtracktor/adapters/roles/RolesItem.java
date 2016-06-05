package com.github.sonerik.bugtracktor.adapters.roles;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.events.ERoleSelectionChanged;
import com.github.sonerik.bugtracktor.models.Role;
import com.github.sonerik.bugtracktor.utils.RxBus;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(of = "role")
@RequiredArgsConstructor
@AllArgsConstructor
public class RolesItem extends AbstractFlexibleItem<RolesViewHolder> {

    private final Role role;
    private final boolean editMode;
    private boolean selected;

    @Override
    public int getLayoutRes() {
        return R.layout.item_roles;
    }

    @Override
    public RolesViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new RolesViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, RolesViewHolder holder, int position, List payloads) {
        holder.setRole(role);
        holder.setEditMode(editMode);
        holder.setSelected(selected);

        holder.layout.setOnClickListener(v -> {
            selected = !selected;
            adapter.notifyItemChanged(position);
            RxBus.publish(new ERoleSelectionChanged(role, selected));
        });
    }

}

