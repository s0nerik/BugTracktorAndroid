package com.github.sonerik.bugtracktor.adapters.users;

import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.events.EUserClicked;
import com.github.sonerik.bugtracktor.models.User;
import com.github.sonerik.bugtracktor.utils.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.viewholders.FlexibleViewHolder;

public class UsersViewHolder extends FlexibleViewHolder {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.subtitle)
    TextView subtitle;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.layout)
    RelativeLayout layout;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.btnOverflow)
    ImageView btnOverflow;

    public UsersViewHolder(View view, FlexibleAdapter adapter) {
        super(view, adapter);
        ButterKnife.bind(this, view);
    }

    public void setUser(User user) {
        if (user != null) {
            title.setText(user.getRealName());
            subtitle.setText("@"+user.getNickname());
            email.setText(user.getEmail());
            Glide.with(avatar.getContext())
                 .load(user.getAvatarUrl())
                 .into(avatar);
        }

        layout.setOnClickListener(v -> RxBus.publish(new EUserClicked(user, EUserClicked.Type.ITEM)));
        btnOverflow.setOnClickListener(v -> {
            PopupMenu menu = new PopupMenu(btnOverflow.getContext(), btnOverflow, Gravity.RIGHT);
            menu.inflate(R.menu.edit_project_member);
            menu.setOnMenuItemClickListener(item -> {
                RxBus.publish(new EUserClicked(user, EUserClicked.Type.REMOVE));
                return true;
            });
            menu.show();
        });
    }

    public void setEditMode(boolean editMode) {
        btnOverflow.setVisibility(editMode ? View.VISIBLE : View.GONE);
    }
}

