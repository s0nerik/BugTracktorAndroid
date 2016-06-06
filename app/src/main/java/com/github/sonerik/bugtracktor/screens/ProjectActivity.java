package com.github.sonerik.bugtracktor.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.f2prateek.dart.InjectExtra;
import com.github.sonerik.bugtracktor.App;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.adapters.issues.IssuesAdapter;
import com.github.sonerik.bugtracktor.adapters.issues.IssuesItem;
import com.github.sonerik.bugtracktor.adapters.project_members.ProjectMembersAdapter;
import com.github.sonerik.bugtracktor.adapters.project_members.ProjectMembersItem;
import com.github.sonerik.bugtracktor.api.BugTracktorApi;
import com.github.sonerik.bugtracktor.bundlers.ParcelBundler;
import com.github.sonerik.bugtracktor.events.EIssueClicked;
import com.github.sonerik.bugtracktor.events.EProjectMemberClicked;
import com.github.sonerik.bugtracktor.events.EProjectMemberCreated;
import com.github.sonerik.bugtracktor.models.Issue;
import com.github.sonerik.bugtracktor.models.Project;
import com.github.sonerik.bugtracktor.models.ProjectMember;
import com.github.sonerik.bugtracktor.models.User;
import com.github.sonerik.bugtracktor.rx_adapter.BindableRxList;
import com.github.sonerik.bugtracktor.screens.base.EditableActivity;
import com.github.sonerik.bugtracktor.utils.EditTextUtils;
import com.github.sonerik.bugtracktor.utils.Rx;
import com.github.sonerik.bugtracktor.utils.RxBus;
import com.google.common.collect.ImmutableMap;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by sonerik on 5/29/16.
 */
public class ProjectActivity extends EditableActivity {

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
    @BindView(R.id.icAddMember)
    ImageView icAddMember;

    @InjectExtra
    @State(ParcelBundler.class)
    Project project;

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
        initListeners();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (savedInstanceState == null) {
            init();
            loadProjectData();
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
                    setMode(Mode.EDIT, true);
                    updateMembers();
                    updateIssues();
                    break;
                case R.id.save:
                    saveChanges();
                    break;
            }
            return true;
        });

        etProjectName.setText(project.getName());
        etProjectShortDescription.setText(project.getShortDescription());

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

        setMode(mode, false);
    }

    private void initListeners() {
        RxBus.on(EIssueClicked.class)
             .compose(bindToLifecycle())
             .subscribe(e -> {
                 // TODO: allow editing only for those who really can
                 startActivity(Henson.with(this)
                                     .gotoIssueActivity()
                                     .canManage(true)
                                     .issue(e.issue)
                                     .mode(EditableActivity.Mode.VIEW)
                                     .build());
             });
        RxBus.on(EProjectMemberClicked.class)
             .filter(e -> e.type == EProjectMemberClicked.Type.REMOVE)
             .compose(bindToLifecycle())
             .subscribe(e -> {
                 project.getMembers().remove(e.member);
                 projectMembers.remove(new ProjectMembersItem(e.member, canEdit()));
             });
        RxBus.on(EProjectMemberCreated.class)
             .compose(bindToLifecycle())
             .subscribe(e -> {
                 project.getMembers().add(e.member);
                 projectMembers.add(new ProjectMembersItem(e.member, canEdit()));
             });
        RxTextView.textChanges(etProjectName)
                  .compose(bindToLifecycle())
                  .subscribe(text -> project.setName(text.toString()));
        RxTextView.textChanges(etProjectShortDescription)
                  .compose(bindToLifecycle())
                  .subscribe(text -> project.setShortDescription(text.toString()));
        // TODO: full description tracking
    }

    @OnClick(R.id.icAddMember)
    public void onAddMember() {
        startActivity(Henson.with(this).gotoAddMemberActivity().project(project).build());
    }

    private void updateMembers() {
        projectMembers.clear();
        List<ProjectMember> members = project.getMembers();
        if (members != null && !members.isEmpty()) {
            layoutMembersEmpty.setVisibility(View.GONE);
            for (ProjectMember projectMember : members) {
                projectMembers.add(new ProjectMembersItem(projectMember, canEdit()));
            }
        } else {
            layoutMembersEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void updateIssues() {
        issueItems.clear();
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
        init();
    }

    @Override
    protected int getMenu(Mode mode) {
        switch (mode) {
            case CREATE:
                return R.menu.project_edit;
            case EDIT:
                return R.menu.project_edit;
            case VIEW:
                return R.menu.project_normal;
            default:
                return R.menu.project_normal;
        }
    }

    @Override
    protected void setMode(Mode mode, boolean expandToolbar) {
        super.setMode(mode, expandToolbar);
        icAddMember.setVisibility(canEdit() ? View.VISIBLE : View.GONE);
        EditTextUtils.setEditingEnabled(etProjectName, canEdit(), false);
        EditTextUtils.setEditingEnabled(etProjectShortDescription, canEdit(), true);
    }

    private void saveChanges() {
        api.updateProject(project.getId(), project)
           .compose(bindToLifecycle())
           .compose(Rx.applySchedulers())
           .doOnSubscribe(() -> setMode(Mode.VIEW, false))
           .doOnSubscribe(() -> progress.setVisibility(View.VISIBLE))
           .doOnNext(project -> this.project = project)
           .doOnTerminate(() -> progress.setVisibility(View.GONE))
           .doOnTerminate(this::updateIssues)
           .doOnTerminate(this::updateMembers)
           .subscribe(project -> init());
    }

    private void loadProjectData() {
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
