package com.github.sonerik.bugtracktor.adapters.projects;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.events.EProjectClicked;
import com.github.sonerik.bugtracktor.models.Project;
import com.github.sonerik.bugtracktor.utils.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.viewholders.FlexibleViewHolder;

public class ProjectsViewHolder extends FlexibleViewHolder {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.subtitle)
    TextView subtitle;
    @BindView(R.id.layout)
    RelativeLayout layout;

    public ProjectsViewHolder(View view, FlexibleAdapter adapter) {
        super(view, adapter);
        ButterKnife.bind(this, view);
    }

    public void setProject(Project project) {
        title.setText(project.getName());
        subtitle.setText(project.getShortDescription());
        layout.setOnClickListener(v -> RxBus.publish(new EProjectClicked(project)));
    }
}

