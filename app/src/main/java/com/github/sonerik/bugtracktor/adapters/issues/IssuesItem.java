package com.github.sonerik.bugtracktor.adapters.issues;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.models.Issue;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IssuesItem extends AbstractFlexibleItem<IssuesViewHolder> {

    private final Issue issue;

    @Override
    public int getLayoutRes() {
        return R.layout.item_project_members;
    }

    @Override
    public IssuesViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new IssuesViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, IssuesViewHolder holder, int position, List payloads) {
        holder.setIssue(issue);
    }

    @Override
    public boolean equals(Object o) {
        return issue.equals(o);
    }

}

