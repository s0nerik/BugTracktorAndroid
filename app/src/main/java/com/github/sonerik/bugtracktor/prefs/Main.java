package com.github.sonerik.bugtracktor.prefs;

import net.yslibrary.simplepreferences.annotation.Key;
import net.yslibrary.simplepreferences.annotation.Preferences;

/**
 * Created by sonerik on 5/28/16.
 */
@Preferences
public class Main {
    @Key
    protected String token = "";
    @Key
    protected String email = "";
    @Key
    protected String password = "";
}
