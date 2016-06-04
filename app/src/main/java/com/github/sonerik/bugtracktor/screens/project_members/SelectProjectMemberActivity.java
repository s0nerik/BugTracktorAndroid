package com.github.sonerik.bugtracktor.screens.project_members;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.github.sonerik.bugtracktor.App;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.adapters.project_members.ProjectMembersAdapter;
import com.github.sonerik.bugtracktor.adapters.project_members.ProjectMembersItem;
import com.github.sonerik.bugtracktor.api.BugTracktorApi;
import com.github.sonerik.bugtracktor.models.ProjectMember;
import com.github.sonerik.bugtracktor.rx_adapter.BindableRxList;
import com.github.sonerik.bugtracktor.screens.base.BaseActivity;
import com.github.sonerik.bugtracktor.utils.Rx;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import icepick.State;
import io.github.kobakei.grenade.annotation.Extra;
import io.github.kobakei.grenade.annotation.Navigator;

/**
 * Created by sonerik on 6/4/16.
 */
@Navigator
public class SelectProjectMemberActivity extends BaseActivity {

    @Inject
    BugTracktorApi api;

    @Extra
    @State
    int projectId;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    private BindableRxList<ProjectMembersItem> items = new BindableRxList<>();
    private ProjectMembersAdapter adapter = new ProjectMembersAdapter(items);

    @Override
    protected Map<BindableRxList, RecyclerView.Adapter> getBindableLists() {
        return ImmutableMap.of(items, adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_project_member;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get().inject(this);
        SelectProjectMemberActivityNavigator.inject(this, getIntent());
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        recycler.setAdapter(adapter);

        api.getProjectMembers(projectId)
           .compose(bindToLifecycle())
           .compose(Rx.applySchedulers())
           .subscribe(this::initMembers);
    }

    private void initMembers(List<ProjectMember> members) {
        items.clear();
        items.addAll(Collections2.transform(members, ProjectMembersItem::new));
    }
}
