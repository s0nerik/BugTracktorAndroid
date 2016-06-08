package com.github.sonerik.bugtracktor.di.modules;

import android.app.Application;

import com.github.sonerik.bugtracktor.App;
import com.github.sonerik.bugtracktor.models.User;
import com.github.sonerik.bugtracktor.prefs.MainPrefs;
import com.tbruyelle.rxpermissions.RxPermissions;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lombok.RequiredArgsConstructor;
import lombok.val;

/**
 * Created by sonerik on 5/28/16.
 */
@Module
@RequiredArgsConstructor
public class AppModule {
    private final App app;

    @Provides
    @Singleton
    Application provideAppContext() {
        return app;
    }

    @Provides
    @Singleton
    App provideApp() {
        return app;
    }

    @Provides
    @Singleton
    MainPrefs provideMainPrefs() {
        return MainPrefs.create(app);
    }

    @Provides
    User provideCurrentUser(MainPrefs prefs) {
        val user = new User();
        user.setEmail(prefs.getEmail());
        user.setRealName(prefs.getName());
        user.setNickname(prefs.getNickname());
        user.setAvatarUrl(prefs.getAvatarUrl());
        return user;
    }

    @Provides
    @Singleton
    RxPermissions provideRxPermissions() {
        return RxPermissions.getInstance(app);
    }
}
