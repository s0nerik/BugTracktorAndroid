package com.github.sonerik.bugtracktor.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.sonerik.bugtracktor.App;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.adapters.projects.ProjectsAdapter;
import com.github.sonerik.bugtracktor.adapters.projects.ProjectsItem;
import com.github.sonerik.bugtracktor.api.BugTracktorApi;
import com.github.sonerik.bugtracktor.events.EProjectClicked;
import com.github.sonerik.bugtracktor.models.Permission;
import com.github.sonerik.bugtracktor.models.Project;
import com.github.sonerik.bugtracktor.rx_adapter.BindableRxList;
import com.github.sonerik.bugtracktor.screens.base.BaseActivity;
import com.github.sonerik.bugtracktor.utils.Rx;
import com.github.sonerik.bugtracktor.utils.RxBus;
import com.google.common.collect.ImmutableMap;
import com.trello.rxlifecycle.RxLifecycle;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;

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
    @BindView(R.id.progress)
    ProgressBar progress;

    private BindableRxList<ProjectsItem> projectItems = new BindableRxList<>();
    private ProjectsAdapter projectsAdapter = new ProjectsAdapter(projectItems);

    private static boolean wasPaused = false;

    @Override
    protected Map<BindableRxList, RecyclerView.Adapter> getBindableLists() {
        return ImmutableMap.of(projectItems, projectsAdapter);
    }

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
            updateData();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasPaused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasPaused) {
            wasPaused = false;
            updateData();
        }

        RxBus.on(EProjectClicked.class)
             .compose(RxLifecycle.bindActivity(lifecycle()))
             // TODO: figure out whether user can edit project
             .subscribe(e -> {
                 startActivity(Henson.with(this)
                                     .gotoProjectActivity()
                                     .canManage(true)
                                     .mode(ProjectActivity.Mode.VIEW)
                                     .project(e.project)
                                     .build());
             });
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
            updateData();
        }
    }

    private Observable<List<Project>> updateProjects() {
        return api.getProjects()
                  .compose(bindToLifecycle())
                  .compose(Rx.applySchedulers())
                  .doOnSubscribe(() -> progress.setVisibility(View.VISIBLE))
                  .doOnTerminate(() -> progress.setVisibility(View.GONE))
                  .doOnNext(this::initProjects);
    }

    private Observable<Boolean> checkCreateProjectPermission() {
        return api.getPermissions(null)
                  .map(this::checkCreateProjectPermission)
                  .compose(bindToLifecycle())
                  .compose(Rx.applySchedulers())
                  .doOnSubscribe(() -> fab.setVisibility(View.GONE))
                  .doOnNext(this::initFab);
    }

    private void updateData() {
        updateProjects().concatMap(projects -> checkCreateProjectPermission())
                        .subscribe(canCreate -> {});
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
    }

    @OnClick(R.id.fab)
    void onFabClicked() {
        startActivity(new Intent(this, CreateProjectActivity.class));
    }
}
