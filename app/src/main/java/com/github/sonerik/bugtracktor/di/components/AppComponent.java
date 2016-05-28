package com.github.sonerik.bugtracktor.di.components;

import com.github.sonerik.bugtracktor.di.modules.AppModule;
import com.github.sonerik.bugtracktor.di.modules.WebServiceModule;
import com.github.sonerik.bugtracktor.screens.login.LoginActivity;
import com.github.sonerik.bugtracktor.screens.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by sonerik on 5/28/16.
 */
@Singleton
@Component(modules = {AppModule.class, WebServiceModule.class})
public interface AppComponent {
    void inject(MainActivity a);
    void inject(LoginActivity a);
}
