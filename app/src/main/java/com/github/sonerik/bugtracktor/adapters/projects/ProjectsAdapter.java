package com.github.sonerik.bugtracktor.adapters.projects;

import android.support.annotation.NonNull;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;

public class ProjectsAdapter extends FlexibleAdapter<ProjectsItem> {
    public ProjectsAdapter(@NonNull List<ProjectsItem> items) {
        super(items);
    }
}

