package com.github.sonerik.bugtracktor.di.components;

import com.github.sonerik.bugtracktor.di.modules.AppModule;
import com.github.sonerik.bugtracktor.di.modules.WebServiceModule;
import com.github.sonerik.bugtracktor.screens.create_project.CreateProjectActivity;
import com.github.sonerik.bugtracktor.screens.issue.IssueActivity;
import com.github.sonerik.bugtracktor.screens.login.LoginActivity;
import com.github.sonerik.bugtracktor.screens.main.MainActivity;
import com.github.sonerik.bugtracktor.screens.project_issues.ProjectIssuesActivity;
import com.github.sonerik.bugtracktor.screens.project_members.ProjectMembersActivity;
import com.github.sonerik.bugtracktor.screens.project_members.SelectProjectMemberActivity;
import com.github.sonerik.bugtracktor.screens.projects.ProjectActivity;

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
}
