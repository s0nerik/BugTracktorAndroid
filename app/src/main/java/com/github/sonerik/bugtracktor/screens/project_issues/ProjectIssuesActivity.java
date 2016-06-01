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
import com.github.sonerik.bugtracktor.screens.base.BaseActivity;
import com.trello.rxlifecycle.RxLifecycle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Alex on 6/1/2016.
 */
public class ProjectIssuesActivity extends BaseActivity {

    @Inject
    BugTracktorApi api;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    public static final String PROJECT_ID = "PROJECT_ID";

    private List<IssuesItem> issueItems = new ArrayList<>();
    private IssuesAdapter adapter = new IssuesAdapter(issueItems);

    private int projectId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_project_issues;
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

        api.getIssues(projectId, null, null, null)
           .compose(RxLifecycle.bindActivity(lifecycle()))
           .subscribe(issues -> {
               issueItems.clear();
               for (Issue issue : issues) {
                   issueItems.add(new IssuesItem(issue));
               }
               adapter.notifyDataSetChanged();
           });
    }
}
