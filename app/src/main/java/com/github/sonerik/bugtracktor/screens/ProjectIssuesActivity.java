package com.github.sonerik.bugtracktor.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.f2prateek.dart.InjectExtra;
import com.github.s0nerik.rxlist.RxList;
import com.github.sonerik.bugtracktor.App;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.adapters.issues.IssuesAdapter;
import com.github.sonerik.bugtracktor.adapters.issues.IssuesItem;
import com.github.sonerik.bugtracktor.api.BugTracktorApi;
import com.github.sonerik.bugtracktor.models.Issue;
import com.github.sonerik.bugtracktor.screens.base.BaseActivity;
import com.google.common.collect.ImmutableMap;
import com.trello.rxlifecycle.RxLifecycle;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import icepick.State;

/**
 * Created by Alex on 6/1/2016.
 */
public class ProjectIssuesActivity extends BaseActivity {

    @Inject
    BugTracktorApi api;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    private RxList<IssuesItem> issueItems = new RxList<>();
    private IssuesAdapter adapter = new IssuesAdapter(issueItems);

    @InjectExtra
    @State
    int projectId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_project_issues;
    }

    @Override
    protected Map<RxList<?>, RecyclerView.Adapter<?>> getBindableLists() {
        return ImmutableMap.of(issueItems, adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        recycler.setAdapter(adapter);

        api.getIssues(projectId, null, null, null)
           .compose(RxLifecycle.bindActivity(lifecycle()))
           .subscribe(issues -> {
               issueItems.clear();
               for (Issue issue : issues) {
                   issueItems.add(new IssuesItem(issue));
               }
           }, this::handleRequestError);
    }
}
