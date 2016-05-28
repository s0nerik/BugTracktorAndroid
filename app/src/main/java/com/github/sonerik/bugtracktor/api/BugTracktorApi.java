package com.github.sonerik.bugtracktor.api;

import com.github.sonerik.bugtracktor.prefs.MainPrefs;

import java.io.File;

import lombok.RequiredArgsConstructor;

/**
 * Created by sonerik on 5/28/16.
 */
@RequiredArgsConstructor
public class BugTracktorApi {
    private final BugTracktorWebService service;
    private final File cacheDir;
    private final MainPrefs prefs;
}
