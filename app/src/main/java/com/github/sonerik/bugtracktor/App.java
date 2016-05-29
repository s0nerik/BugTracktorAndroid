package com.github.sonerik.bugtracktor;

import android.app.Application;

import com.github.sonerik.bugtracktor.di.components.AppComponent;
import com.github.sonerik.bugtracktor.di.components.DaggerAppComponent;
import com.github.sonerik.bugtracktor.di.modules.AppModule;
import com.github.sonerik.bugtracktor.di.modules.WebServiceModule;

/**
 * Created by sonerik on 5/28/16.
 */
public class App extends Application {
    public static final String TAG = "BugTracktor";

    private static App instance;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initComponents();
    }

    private void initComponents() {
        appComponent = DaggerAppComponent.builder()
                                         .appModule(new AppModule(this))
                                         .webServiceModule(new WebServiceModule())
                                         .build();
    }

    public static AppComponent getComponent() {
        return instance.appComponent;
    }
}
