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
    @BindView(R.id.icRemove)
    ImageView icRemove;
    @BindView(R.id.progress)
    View progress;

    public AttachmentsViewHolder(View view, FlexibleAdapter adapter) {
        super(view, adapter);
        ButterKnife.bind(this, view);
    }

    public void setIssueAttachment(IssueAttachment attachment) {
        if (attachment == null) {
            progress.setVisibility(View.VISIBLE);
            image.setBackgroundResource(R.color.md_grey_800);
        } else {
            progress.setVisibility(View.GONE);
            Glide.with(image.getContext())
                 .load(attachment.getUrl())
                 .into(image);
        }

        layout.setOnClickListener(v -> RxBus.publish(new EAttachmentClicked(attachment, EAttachmentClicked.Type.ITEM)));
        icRemove.setOnClickListener(v -> RxBus.publish(new EAttachmentClicked(attachment, EAttachmentClicked.Type.REMOVE)));
    }
}

