package com.github.sonerik.bugtracktor.events;

import com.github.sonerik.bugtracktor.models.Project;

import lombok.RequiredArgsConstructor;

/**
 * Created by sonerik on 5/29/16.
 */
@RequiredArgsConstructor
public class EProjectClicked {
    public final Project project;
}
