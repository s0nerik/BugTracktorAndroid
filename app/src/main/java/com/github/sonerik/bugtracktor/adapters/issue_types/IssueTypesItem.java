package com.github.sonerik.bugtracktor.adapters.issue_types;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.models.IssueType;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(of = "projectMember")
@RequiredArgsConstructor
public class IssueTypesItem extends AbstractFlexibleItem<IssueTypesViewHolder> {

    private final IssueType issueType;
    private final boolean editMode;

    @Override
    public int getLayoutRes() {
        return R.layout.item_issue_types;
    }

    @Override
    public IssueTypesViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new IssueTypesViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, IssueTypesViewHolder holder, int position, List payloads) {
        holder.setIssueType(issueType);
        holder.setEditMode(false);
    }

}

