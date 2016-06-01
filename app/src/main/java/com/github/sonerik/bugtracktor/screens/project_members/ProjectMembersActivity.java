package com.github.sonerik.bugtracktor.screens.project_members;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.github.sonerik.bugtracktor.App;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.adapters.project_members.ProjectMembersAdapter;
import com.github.sonerik.bugtracktor.adapters.project_members.ProjectMembersItem;
import com.github.sonerik.bugtracktor.api.BugTracktorApi;
import com.github.sonerik.bugtracktor.screens.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Alex on 6/1/2016.
 */
public class ProjectMembersActivity extends BaseActivity {
    @Inject
    BugTracktorApi api;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    public static final String PROJECT_ID = "PROJECT_ID";

    private List<ProjectMembersItem> memberItems = new ArrayList<>();
    private ProjectMembersAdapter adapter = new ProjectMembersAdapter(memberItems);

    private int projectId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_project_members;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);

        if (savedInstanceState == null) {
            projectId = getIntent().getIntExtra(PROJECT_ID, -1);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        recycler.setAdapter(adapter);

        // TODO: get project members
    }
}
