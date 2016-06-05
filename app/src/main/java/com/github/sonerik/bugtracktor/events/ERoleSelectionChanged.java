package com.github.sonerik.bugtracktor.events;

import com.github.sonerik.bugtracktor.models.Role;

import lombok.RequiredArgsConstructor;

/**
 * Created by sonerik on 6/5/16.
 */
@RequiredArgsConstructor
public class ERoleSelectionChanged {
    public final Role role;
    public final boolean selected;
}
