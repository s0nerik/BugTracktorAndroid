package com.github.sonerik.bugtracktor.api;

import com.github.sonerik.bugtracktor.models.Token;
import com.github.sonerik.bugtracktor.prefs.MainPrefs;

import java.io.File;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import rx.Observable;

/**
 * Created by sonerik on 5/28/16.
 */
@RequiredArgsConstructor
public class BugTracktorApi {
    private interface Login {
        Observable<Token> login(String email, String password);
    }

    @Delegate(excludes = Login.class)
    private final BugTracktorWebService service;
    private final File cacheDir;
    private final MainPrefs prefs;

    public boolean isLoggedIn() {
        return !prefs.getToken().isEmpty();
    }

    public void logOut() {
        prefs.setToken("");
    }

    public Observable<Token> login(String email, String password) {
        return service.login(email, password)
                      .doOnNext(result -> prefs.setToken(result.getToken()));
    }
}
