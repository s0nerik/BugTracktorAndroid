package com.github.sonerik.bugtracktor.adapters.project_members;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.models.ProjectMember;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProjectMembersItem extends AbstractFlexibleItem<ProjectMembersViewHolder> {

    private final ProjectMember projectMember;
    private final boolean editMode;

    @Override
    public int getLayoutRes() {
        return R.layout.item_project_members;
    }

    @Override
    public ProjectMembersViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new ProjectMembersViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, ProjectMembersViewHolder holder, int position, List payloads) {
        holder.setProjectMember(projectMember);
        holder.setEditMode(editMode);
    }

    @Override
    public boolean equals(Object o) {
        return projectMember.equals(o);
    }

}

