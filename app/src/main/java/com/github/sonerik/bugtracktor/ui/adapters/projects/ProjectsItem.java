package com.github.sonerik.bugtracktor.ui.adapters.projects;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.models.Project;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProjectsItem extends AbstractFlexibleItem<ProjectsViewHolder> {

    private final Project project;

    @Override
    public int getLayoutRes() {
        return R.layout.item_projects;
    }

    @Override
    public ProjectsViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new ProjectsViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, ProjectsViewHolder holder, int position, List payloads) {
        holder.setProject(project);
    }

    @Override
    public boolean equals(Object o) {
        return project.equals(o);
    }

}

