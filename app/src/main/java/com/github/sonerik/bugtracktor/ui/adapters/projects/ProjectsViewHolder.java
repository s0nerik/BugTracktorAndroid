package com.github.sonerik.bugtracktor.ui.adapters.projects;

import android.view.View;
import android.widget.TextView;

import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.models.Project;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.viewholders.FlexibleViewHolder;

public class ProjectsViewHolder extends FlexibleViewHolder {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.subtitle)
    TextView subtitle;

    public ProjectsViewHolder(View view, FlexibleAdapter adapter) {
        super(view, adapter);
        ButterKnife.bind(this, view);
    }

    public void setProject(Project project) {
        title.setText(project.getName());
        subtitle.setText(project.getShortDescription());
    }
}

