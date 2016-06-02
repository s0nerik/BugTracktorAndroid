package com.github.sonerik.bugtracktor.events;

import com.github.sonerik.bugtracktor.models.IssueAttachment;

import lombok.RequiredArgsConstructor;

/**
 * Created by sonerik on 6/2/16.
 */
@RequiredArgsConstructor
public class EAttachmentClicked {
    public enum Type { ITEM, REMOVE }

    public final IssueAttachment attachment;
    public final Type type;
}
