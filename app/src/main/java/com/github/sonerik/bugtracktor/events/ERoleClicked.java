package com.github.sonerik.bugtracktor.events;

import com.github.sonerik.bugtracktor.models.Role;

import lombok.RequiredArgsConstructor;

/**
 * Created by sonerik on 6/5/16.
 */
@RequiredArgsConstructor
public class ERoleClicked {
    public enum Type { ITEM, REMOVE }

    public final Role role;
    public final Type type;
}
