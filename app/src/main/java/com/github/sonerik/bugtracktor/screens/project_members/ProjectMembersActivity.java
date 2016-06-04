package com.github.sonerik.bugtracktor.screens.project_members;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.github.sonerik.bugtracktor.App;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.adapters.project_members.ProjectMembersAdapter;
import com.github.sonerik.bugtracktor.adapters.project_members.ProjectMembersItem;
import com.github.sonerik.bugtracktor.api.BugTracktorApi;
import com.github.sonerik.bugtracktor.rx_adapter.BindableRxList;
import com.github.sonerik.bugtracktor.screens.base.BaseActivity;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import icepick.State;
import io.github.kobakei.grenade.annotation.Extra;
import io.github.kobakei.grenade.annotation.Navigator;

/**
 * Created by Alex on 6/1/2016.
 */
@Navigator
public class ProjectMembersActivity extends BaseActivity {
    @Inject
    BugTracktorApi api;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    private BindableRxList<ProjectMembersItem> memberItems = new BindableRxList<>();
    private ProjectMembersAdapter adapter = new ProjectMembersAdapter(memberItems);

    @Extra
    @State
    int projectId;

    @Override
    protected Map<BindableRxList, RecyclerView.Adapter> getBindableLists() {
        return ImmutableMap.of(memberItems, adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_project_members;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        ProjectMembersActivityNavigator.inject(this, getIntent());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        recycler.setAdapter(adapter);

        // TODO: get project members
    }
}
