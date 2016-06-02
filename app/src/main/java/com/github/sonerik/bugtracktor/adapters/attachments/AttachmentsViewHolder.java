package com.github.sonerik.bugtracktor.adapters.attachments;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.events.EAttachmentClicked;
import com.github.sonerik.bugtracktor.models.IssueAttachment;
import com.github.sonerik.bugtracktor.utils.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.viewholders.FlexibleViewHolder;

public class AttachmentsViewHolder extends FlexibleViewHolder {
    @BindView(R.id.layout)
    View layout;
    @BindView(R.id.image)
    ImageView image;

    public AttachmentsViewHolder(View view, FlexibleAdapter adapter) {
        super(view, adapter);
        ButterKnife.bind(this, view);
    }

    public void setIssueAttachment(IssueAttachment attachment) {
        Glide.with(image.getContext())
             .load(attachment.getUrl())
             .placeholder(R.color.md_grey_800)
             .into(image);
        layout.setOnClickListener(v -> RxBus.publish(new EAttachmentClicked(attachment)));
    }
}

