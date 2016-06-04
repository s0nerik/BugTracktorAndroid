package com.github.sonerik.bugtracktor.screens.project_issues;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.github.sonerik.bugtracktor.App;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.adapters.issues.IssuesAdapter;
import com.github.sonerik.bugtracktor.adapters.issues.IssuesItem;
import com.github.sonerik.bugtracktor.api.BugTracktorApi;
import com.github.sonerik.bugtracktor.models.Issue;
import com.github.sonerik.bugtracktor.rx_adapter.BindableRxList;
import com.github.sonerik.bugtracktor.screens.base.BaseActivity;
import com.google.common.collect.ImmutableMap;
import com.trello.rxlifecycle.RxLifecycle;

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
public class ProjectIssuesActivity extends BaseActivity {

    @Inject
    BugTracktorApi api;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    private BindableRxList<IssuesItem> issueItems = new BindableRxList<>();
    private IssuesAdapter adapter = new IssuesAdapter(issueItems);

    @Extra
    @State
    int projectId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_project_issues;
    }

    @Override
    protected Map<BindableRxList, RecyclerView.Adapter> getBindableLists() {
        return ImmutableMap.of(issueItems, adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        ProjectIssuesActivityNavigator.inject(this, getIntent());
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
           });
    }
}
