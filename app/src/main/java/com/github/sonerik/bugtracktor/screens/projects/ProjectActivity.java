package com.github.sonerik.bugtracktor.screens.projects;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.sonerik.bugtracktor.App;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.adapters.issues.IssuesAdapter;
import com.github.sonerik.bugtracktor.adapters.issues.IssuesItem;
import com.github.sonerik.bugtracktor.adapters.project_members.ProjectMembersAdapter;
import com.github.sonerik.bugtracktor.adapters.project_members.ProjectMembersItem;
import com.github.sonerik.bugtracktor.api.BugTracktorApi;
import com.github.sonerik.bugtracktor.bundlers.ProjectBundler;
import com.github.sonerik.bugtracktor.events.EIssueClicked;
import com.github.sonerik.bugtracktor.models.Issue;
import com.github.sonerik.bugtracktor.models.Project;
import com.github.sonerik.bugtracktor.models.ProjectMember;
import com.github.sonerik.bugtracktor.models.User;
import com.github.sonerik.bugtracktor.rx_adapter.BindableRxList;
import com.github.sonerik.bugtracktor.screens.base.BaseActivity;
import com.github.sonerik.bugtracktor.screens.issue.IssueActivityNavigator;
import com.github.sonerik.bugtracktor.utils.Rx;
import com.github.sonerik.bugtracktor.utils.RxBus;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import icepick.State;
import io.github.kobakei.grenade.annotation.Extra;
import io.github.kobakei.grenade.annotation.Navigator;

/**
 * Created by sonerik on 5/29/16.
 */
@Navigator
public class ProjectActivity extends BaseActivity {
    @Inject
    BugTracktorApi api;

    @BindView(R.id.etProjectName)
    EditText etProjectName;
    @BindView(R.id.etProjectShortDescription)
    EditText etProjectShortDescription;
    @BindView(R.id.mainToolbar)
    Toolbar mainToolbar;
    @BindView(R.id.mainCollapsing)
    CollapsingToolbarLayout mainCollapsing;
    @BindView(R.id.mainAppbar)
    AppBarLayout mainAppbar;
    @BindView(R.id.labelAuthor)
    TextView labelAuthor;
    @BindView(R.id.txtAuthor)
    TextView txtAuthor;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.tiProjectName)
    TextInputLayout tiProjectName;
    @BindView(R.id.tiProjectShortDescription)
    TextInputLayout tiProjectShortDescription;
    @BindView(R.id.labelMembers)
    TextView labelMembers;
    @BindView(R.id.rvMembers)
    RecyclerView rvMembers;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.layoutMembersEmpty)
    LinearLayout layoutMembersEmpty;
    @BindView(R.id.rvIssues)
    RecyclerView rvIssues;
    @BindView(R.id.layoutIssuesEmpty)
    LinearLayout layoutIssuesEmpty;
    @BindView(R.id.btnViewAllMembers)
    Button btnViewAllMembers;
    @BindView(R.id.membersLoadingView)
    TextView membersLoadingView;
    @BindView(R.id.btnViewAllIssues)
    Button btnViewAllIssues;
    @BindView(R.id.issuesLoadingView)
    TextView issuesLoadingView;

    @Extra
    @State(ProjectBundler.class)
    Project project;

    @State
    boolean editMode;

    private BindableRxList<ProjectMembersItem> projectMembers = new BindableRxList<>();
    private ProjectMembersAdapter projectMembersAdapter = new ProjectMembersAdapter(projectMembers);

    private BindableRxList<IssuesItem> issueItems = new BindableRxList<>();
    private IssuesAdapter issuesAdapter = new IssuesAdapter(issueItems);

    @Override
    protected Map<BindableRxList, RecyclerView.Adapter> getBindableLists() {
        return ImmutableMap.of(issueItems, issuesAdapter, projectMembers, projectMembersAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_project;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        ProjectActivityNavigator.inject(this, getIntent());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (savedInstanceState == null) {
            init();
            updateProject();
        } else {
            init();
        }
    }

    private void init() {
        Log.d(App.TAG, project.toString());

        mainCollapsing.setTitle(project.getName());
        mainToolbar.inflateMenu(R.menu.project_normal);
        mainToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mainToolbar.setNavigationOnClickListener(v -> finish());
        mainToolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.edit:
                    setEditMode(true);
                    break;
                case R.id.save:
                    setEditMode(false);
                    break;
            }
            return true;
        });

        etProjectName.setText(project.getName());
        etProjectShortDescription.setText(project.getShortDescription());
//        txtAuthor.setText(project.get);

        User creator = project.getCreator();
        if (creator != null) {
            txtAuthor.setText(creator.getRealName());
        }

        rvMembers.setAdapter(projectMembersAdapter);
        rvMembers.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = rvMembers.getLayoutManager();
        layoutManager.setAutoMeasureEnabled(true);

        rvIssues.setAdapter(issuesAdapter);
        rvIssues.setNestedScrollingEnabled(false);
        layoutManager = rvMembers.getLayoutManager();
        layoutManager.setAutoMeasureEnabled(true);

        setEditMode(false);
    }

    private void initListeners() {
        RxBus.on(EIssueClicked.class)
             .compose(bindToLifecycle())
             .compose(Rx.applySchedulers())
             .subscribe(e -> {
                 // TODO: allow editing only for those who really can
                 startActivity(new IssueActivityNavigator(true, e.issue).build(this));
             });
    }

    private void updateMembers() {
        List<ProjectMember> members = project.getMembers();
        if (members != null && !members.isEmpty()) {
            layoutMembersEmpty.setVisibility(View.GONE);
            for (ProjectMember projectMember : members) {
                projectMembers.add(new ProjectMembersItem(projectMember, editMode));
            }
        } else {
            layoutMembersEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void updateIssues() {
        List<Issue> issues = project.getIssues();
        if (issues != null && !issues.isEmpty()) {
            layoutIssuesEmpty.setVisibility(View.GONE);
            for (Issue issue : issues) {
                issueItems.add(new IssuesItem(issue));
            }
        } else {
            layoutIssuesEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEditMode(false);
        initListeners();
    }

    private void setEditMode(boolean state) {
        editMode = state;

        mainToolbar.getMenu().clear();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mainAppbar.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();

        if (behavior == null) {
            behavior = new AppBarLayout.Behavior();
            params.setBehavior(behavior);
        }

        if (state) {
            etProjectName.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
            etProjectShortDescription.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);

            mainToolbar.inflateMenu(R.menu.project_edit);
            behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                @Override
                public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                    return false;
                }
            });
            mainAppbar.setExpanded(true, true);
            nestedScrollView.fullScroll(View.FOCUS_UP);

            nestedScrollView.setOnTouchListener((View v, MotionEvent event) -> true);

            etProjectName.setFocusable(true);
            etProjectName.setFocusableInTouchMode(true);
            etProjectShortDescription.setFocusable(true);
            etProjectShortDescription.setFocusableInTouchMode(true);
        } else {
            etProjectName.setBackgroundTintMode(PorterDuff.Mode.CLEAR);
            etProjectShortDescription.setBackgroundTintMode(PorterDuff.Mode.CLEAR);

            mainToolbar.inflateMenu(R.menu.project_normal);
            behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                @Override
                public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                    return true;
                }
            });

            nestedScrollView.setOnTouchListener((View v, MotionEvent event) -> false);

            etProjectName.setFocusable(false);
            etProjectName.setFocusableInTouchMode(false);
            etProjectShortDescription.setFocusable(false);
            etProjectShortDescription.setFocusableInTouchMode(false);
        }
    }

    private void updateProject() {
        api.getProject(project.getId())
           .compose(Rx.applySchedulers())
           .compose(bindToLifecycle())
           .doOnSubscribe(() -> progress.setVisibility(View.VISIBLE))
           .doOnSubscribe(() -> issuesLoadingView.setVisibility(View.VISIBLE))
           .doOnSubscribe(() -> membersLoadingView.setVisibility(View.VISIBLE))
           .doOnNext(p -> project = p)
           .doOnTerminate(() -> issuesLoadingView.setVisibility(View.GONE))
           .doOnTerminate(() -> membersLoadingView.setVisibility(View.GONE))
           .doOnTerminate(() -> progress.setVisibility(View.GONE))
           .doOnTerminate(this::updateIssues)
           .doOnTerminate(this::updateMembers)
           .subscribe(p -> init());
    }
}
