package com.github.sonerik.bugtracktor.events;

import com.github.sonerik.bugtracktor.models.Issue;

import lombok.RequiredArgsConstructor;

/**
 * Created by sonerik on 6/8/16.
 */
@RequiredArgsConstructor
public class EIssueChanged {
    public enum Type { CREATED, UPDATED }

    public final Issue issue;
    public final Type type;
}
