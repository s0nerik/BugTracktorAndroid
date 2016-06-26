package com.github.sonerik.bugtracktor.adapters.roles;

import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.s0nerik.rxbus.RxBus;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.events.ERoleClicked;
import com.github.sonerik.bugtracktor.models.Role;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.viewholders.FlexibleViewHolder;
import ru.noties.debug.Debug;

public class RolesViewHolder extends FlexibleViewHolder {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.subtitle)
    TextView subtitle;
    @BindView(R.id.layout)
    RelativeLayout layout;
    @BindView(R.id.btnOverflow)
    ImageView btnOverflow;
    @BindView(R.id.checkbox)
    ImageView checkbox;

    public RolesViewHolder(View view, FlexibleAdapter adapter) {
        super(view, adapter);
        ButterKnife.bind(this, view);
    }

    public void setRole(Role role) {
        Debug.d("setRole: \n"+role);

        if (role != null) {
            title.setText(role.getName());
            subtitle.setText(role.getDescription());
        }

        btnOverflow.setOnClickListener(v -> {
            PopupMenu menu = new PopupMenu(btnOverflow.getContext(), btnOverflow, Gravity.RIGHT);
            menu.inflate(R.menu.edit_project_member);
            menu.setOnMenuItemClickListener(item -> {
                RxBus.post(new ERoleClicked(role, ERoleClicked.Type.REMOVE));
                return true;
            });
            menu.show();
        });
    }

    public void setEditMode(boolean editMode) {
        btnOverflow.setVisibility(editMode ? View.VISIBLE : View.GONE);
    }

    public void setSelected(boolean selected) {
//        layout.setBackgroundResource(selected ? R.color.md_grey_100 : R.color.md_white);
        checkbox.setImageResource(selected ? R.drawable.checkbox_marked : R.drawable.checkbox_blank_outline);
    }
}

