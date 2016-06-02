package com.github.sonerik.bugtracktor.adapters.project_members;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.events.EProjectMemberClicked;
import com.github.sonerik.bugtracktor.models.ProjectMember;
import com.github.sonerik.bugtracktor.models.Role;
import com.github.sonerik.bugtracktor.utils.RxBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.viewholders.FlexibleViewHolder;
import lombok.val;

public class ProjectMembersViewHolder extends FlexibleViewHolder {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.subtitle)
    TextView subtitle;
    @BindView(R.id.role)
    TextView role;
    @BindView(R.id.layout)
    RelativeLayout layout;
    @BindView(R.id.avatar)
    ImageView avatar;

    public ProjectMembersViewHolder(View view, FlexibleAdapter adapter) {
        super(view, adapter);
        ButterKnife.bind(this, view);
    }

    public void setProjectMember(ProjectMember projectMember) {
        val user = projectMember.getUser();
        if (user != null) {
            title.setText(user.getRealName());
            subtitle.setText("@"+user.getNickname());
            Glide.with(avatar.getContext())
                 .load(user.getAvatarUrl())
                 .into(avatar);
        }

        List<Role> roles = projectMember.getRoles();
        if (roles != null) {
            for (Role r : roles) {
                role.setText(r.getName());
            }
        }

        layout.setOnClickListener(v -> RxBus.publish(new EProjectMemberClicked(projectMember)));
    }
}

