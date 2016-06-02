package com.github.sonerik.bugtracktor.adapters.attachments;

import android.support.annotation.NonNull;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;

public class AttachmentsAdapter extends FlexibleAdapter<AttachmentsItem> {
    public AttachmentsAdapter(@NonNull List<AttachmentsItem> items) {
        super(items);
    }
}

