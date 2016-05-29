package com.github.sonerik.bugtracktor.screens.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.sonerik.bugtracktor.App;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.api.BugTracktorApi;
import com.github.sonerik.bugtracktor.models.Permission;
import com.github.sonerik.bugtracktor.models.Project;
import com.github.sonerik.bugtracktor.screens.base.BaseActivity;
import com.github.sonerik.bugtracktor.screens.create_project.CreateProjectActivity;
import com.github.sonerik.bugtracktor.screens.login.LoginActivity;
import com.github.sonerik.bugtracktor.ui.adapters.projects.ProjectsAdapter;
import com.github.sonerik.bugtracktor.ui.adapters.projects.ProjectsItem;
import com.github.sonerik.bugtracktor.utils.Rx;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by sonerik on 5/28/16.
 */
public class MainActivity extends BaseActivity {
    @Inject
    BugTracktorApi api;

    @BindView(R.id.rvProjects)
    RecyclerView rvProjects;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private List<ProjectsItem> projectItems = new ArrayList<>();
    private ProjectsAdapter projectsAdapter = new ProjectsAdapter(projectItems);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        rvProjects.setAdapter(projectsAdapter);

        if (!api.isLoggedIn()) {
            startActivityForResult(new Intent(this, LoginActivity.class), LoginActivity.REQUEST_LOGIN);
        } else {
            updateProjects();
            checkCreateProjectPermission();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                api.logOut();
                finish();
                startActivity(getIntent());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LoginActivity.REQUEST_LOGIN && resultCode == RESULT_OK) {
            Toast.makeText(this, "Logged in successfully!", Toast.LENGTH_LONG).show();
            updateProjects();
            checkCreateProjectPermission();
        }
    }

    private void updateProjects() {
        sub.add(
                api.getProjects()
                   .compose(Rx.applySchedulers())
                   .subscribe(this::initProjects)
        );
    }

    private void checkCreateProjectPermission() {
        sub.add(
                api.getPermissions(null)
                   .map(this::checkCreateProjectPermission)
                   .compose(Rx.applySchedulers())
                   .subscribe(this::initFab)
        );
    }

    private boolean checkCreateProjectPermission(List<Permission> permissions) {
        for (Permission permission : permissions) {
            if (permission.getName() != null && permission.getName().equals("create_project")) {
                return true;
            }
        }
        return false;
    }

    private void initFab(boolean visibility) {
        fab.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void initProjects(List<Project> projects) {
        projectItems.clear();
        for (Project project : projects) {
            projectItems.add(new ProjectsItem(project));
        }
        projectsAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fab)
    void onFabClicked() {
        startActivity(new Intent(this, CreateProjectActivity.class));
    }
}
