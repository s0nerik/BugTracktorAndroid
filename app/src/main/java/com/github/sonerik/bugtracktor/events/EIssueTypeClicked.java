package com.github.sonerik.bugtracktor.events;

import com.github.sonerik.bugtracktor.models.IssueType;

import lombok.RequiredArgsConstructor;

/**
 * Created by sonerik on 6/2/16.
 */
@RequiredArgsConstructor
public class EIssueTypeClicked {
    public enum Type { ITEM, REMOVE }

    public final IssueType issueType;
    public final Type type;
}
