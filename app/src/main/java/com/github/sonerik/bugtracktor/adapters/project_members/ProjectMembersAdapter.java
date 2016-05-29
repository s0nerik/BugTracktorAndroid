package com.github.sonerik.bugtracktor.adapters.project_members;

import android.support.annotation.NonNull;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;

public class ProjectMembersAdapter extends FlexibleAdapter<ProjectMembersItem> {
    public ProjectMembersAdapter(@NonNull List<ProjectMembersItem> items) {
        super(items);
    }
}

