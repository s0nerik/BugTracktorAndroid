package com.github.sonerik.bugtracktor.events;

import com.github.sonerik.bugtracktor.models.Issue;

import lombok.RequiredArgsConstructor;

/**
 * Created by sonerik on 6/2/16.
 */
@RequiredArgsConstructor
public class EIssueClicked {
    public final Issue issue;
}
