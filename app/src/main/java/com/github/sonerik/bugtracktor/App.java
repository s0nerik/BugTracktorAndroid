package com.github.sonerik.bugtracktor;

import android.app.Application;

import com.github.sonerik.bugtracktor.di.components.AppComponent;
import com.github.sonerik.bugtracktor.di.components.DaggerAppComponent;

/**
 * Created by sonerik on 5/28/16.
 */
public class App extends Application {
    private static App instance;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initComponents();
    }

    private void initComponents() {
        appComponent = DaggerAppComponent.builder().build();
    }

    public static AppComponent getComponent() {
        return instance.appComponent;
    }
}
