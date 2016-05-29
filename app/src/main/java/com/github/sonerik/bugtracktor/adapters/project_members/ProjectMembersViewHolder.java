package com.github.sonerik.bugtracktor.adapters.project_members;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.models.ProjectMember;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.viewholders.FlexibleViewHolder;

public class ProjectMembersViewHolder extends FlexibleViewHolder {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.subtitle)
    TextView subtitle;
    @BindView(R.id.role)
    TextView role;
    @BindView(R.id.layout)
    RelativeLayout layout;

    public ProjectMembersViewHolder(View view, FlexibleAdapter adapter) {
        super(view, adapter);
        ButterKnife.bind(this, view);
    }

    public void setProjectMember(ProjectMember projectMember) {
        title.setText(projectMember.getUser().getRealName());
        subtitle.setText("@"+projectMember.getUser().getNickname());
        role.setText("Developer");
    }
}

