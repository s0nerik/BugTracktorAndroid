package com.github.sonerik.bugtracktor.adapters.attachments;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.models.IssueAttachment;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AttachmentsItem extends AbstractFlexibleItem<AttachmentsViewHolder> {

    private final IssueAttachment issueAttachment;

    @Override
    public int getLayoutRes() {
        return R.layout.item_issues;
    }

    @Override
    public AttachmentsViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new AttachmentsViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, AttachmentsViewHolder holder, int position, List payloads) {
        holder.setIssueAttachment(issueAttachment);
    }

    @Override
    public boolean equals(Object o) {
        return issueAttachment.equals(o);
    }

}

