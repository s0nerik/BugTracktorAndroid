package com.github.sonerik.bugtracktor.events;

import com.github.sonerik.bugtracktor.models.User;

import lombok.RequiredArgsConstructor;

/**
 * Created by sonerik on 6/5/16.
 */
@RequiredArgsConstructor
public class EUserClicked {
    public enum Type { ITEM, REMOVE }

    public final User user;
    public final Type type;
}
