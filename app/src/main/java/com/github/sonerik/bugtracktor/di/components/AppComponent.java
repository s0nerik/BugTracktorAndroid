package com.github.sonerik.bugtracktor.di.components;

import com.github.sonerik.bugtracktor.di.modules.AppModule;
import com.github.sonerik.bugtracktor.di.modules.WebServiceModule;
import com.github.sonerik.bugtracktor.screens.SelectRolesFragment;
import com.github.sonerik.bugtracktor.screens.SelectUserFragment;
import com.github.sonerik.bugtracktor.screens.CreateProjectActivity;
import com.github.sonerik.bugtracktor.screens.IssueActivity;
import com.github.sonerik.bugtracktor.screens.LoginActivity;
import com.github.sonerik.bugtracktor.screens.MainActivity;
import com.github.sonerik.bugtracktor.screens.ProjectIssuesActivity;
import com.github.sonerik.bugtracktor.screens.ProjectMembersActivity;
import com.github.sonerik.bugtracktor.screens.SelectProjectMemberActivity;
import com.github.sonerik.bugtracktor.screens.ProjectActivity;

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
    void inject(CreateProjectActivity a);
    void inject(ProjectActivity a);
    void inject(ProjectIssuesActivity a);
    void inject(ProjectMembersActivity a);
    void inject(IssueActivity a);
    void inject(SelectProjectMemberActivity a);

    void inject(SelectUserFragment f);
    void inject(SelectRolesFragment f);
}
