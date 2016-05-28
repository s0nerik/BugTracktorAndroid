package com.github.sonerik.bugtracktor.di.components;

import com.github.sonerik.bugtracktor.di.modules.AppModule;
import com.github.sonerik.bugtracktor.di.modules.WebServiceModule;
import com.github.sonerik.bugtracktor.screens.main.MainActivity;

import dagger.Component;

/**
 * Created by sonerik on 5/28/16.
 */
@Component(modules = {AppModule.class, WebServiceModule.class})
public interface AppComponent {
    void inject(MainActivity a);
}
