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
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.adapters.project_members.ProjectMembersAdapter;
import com.github.sonerik.bugtracktor.adapters.project_members.ProjectMembersItem;
import com.github.sonerik.bugtracktor.models.Project;
import com.github.sonerik.bugtracktor.models.ProjectMember;
import com.github.sonerik.bugtracktor.models.User;
import com.github.sonerik.bugtracktor.screens.base.BaseActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import lombok.val;

/**
 * Created by sonerik on 5/29/16.
 */
public class ProjectActivity extends BaseActivity {
    public static final String EXTRA_PROJECT = "project";

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

    private Project project;

    private List<ProjectMembersItem> projectMembers = new ArrayList<>();
    private ProjectMembersAdapter adapter = new ProjectMembersAdapter(projectMembers);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_project;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            project = new Gson().fromJson(getIntent().getStringExtra(EXTRA_PROJECT), Project.class);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
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

        rvMembers.setAdapter(adapter);
        rvMembers.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = rvMembers.getLayoutManager();
        layoutManager.setAutoMeasureEnabled(true);

        for (int i = 0; i < 10; i++) {
            val projectMember = new ProjectMember();
            val user = new User();
            user.setRealName("Vasya Pupkin");
            user.setNickname("pupkin_saloed");
            user.setAvatarUrl("http://api.adorable.io/avatar/256/"+(i+1));
            projectMember.setUser(user);
            projectMembers.add(new ProjectMembersItem(projectMember));
        }

        adapter.notifyDataSetChanged();

        setEditMode(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEditMode(false);
    }

    private void setEditMode(boolean state) {
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_PROJECT, new Gson().toJson(project));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        project = new Gson().fromJson(savedInstanceState.getString(EXTRA_PROJECT), Project.class);
    }
}
