package com.github.sonerik.bugtracktor.di.modules;

import android.app.Application;

import com.github.sonerik.bugtracktor.App;
import com.github.sonerik.bugtracktor.prefs.MainPrefs;
import com.tbruyelle.rxpermissions.RxPermissions;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lombok.RequiredArgsConstructor;

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
    @Singleton
    RxPermissions provideRxPermissions() {
        return RxPermissions.getInstance(app);
    }
}
