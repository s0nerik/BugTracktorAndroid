package com.github.sonerik.bugtracktor.screens.issue;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.sonerik.bugtracktor.App;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.adapters.attachments.AttachmentsAdapter;
import com.github.sonerik.bugtracktor.adapters.attachments.AttachmentsItem;
import com.github.sonerik.bugtracktor.api.BugTracktorApi;
import com.github.sonerik.bugtracktor.models.Issue;
import com.github.sonerik.bugtracktor.models.IssueAttachment;
import com.github.sonerik.bugtracktor.models.User;
import com.github.sonerik.bugtracktor.screens.base.BaseActivity;
import com.github.sonerik.bugtracktor.ui.views.DummyNestedScrollView;
import com.github.sonerik.bugtracktor.ui.views.TintableMenuToolbar;
import com.github.sonerik.bugtracktor.utils.Rx;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by sonerik on 6/2/16.
 */
public class IssueActivity extends BaseActivity {

    public static final String EXTRA_CAN_MANAGE = "EXTRA_CAN_MANAGE";
    public static final String EXTRA_ISSUE = "EXTRA_ISSUE";

    @Inject
    BugTracktorApi api;

    @BindView(R.id.etShortDescription)
    EditText etShortDescription;
    @BindView(R.id.tiShortDescription)
    TextInputLayout tiShortDescription;
    @BindView(R.id.mainToolbar)
    TintableMenuToolbar mainToolbar;
    @BindView(R.id.mainCollapsing)
    CollapsingToolbarLayout mainCollapsing;
    @BindView(R.id.mainAppbar)
    AppBarLayout mainAppbar;
    @BindView(R.id.labelAuthor)
    TextView labelAuthor;
    @BindView(R.id.txtAuthor)
    TextView txtAuthor;
    @BindView(R.id.icAssignee)
    ImageView icAssignee;
    @BindView(R.id.icChangeAssignee)
    ImageView icChangeAssignee;
    @BindView(R.id.labelAssignee)
    TextView labelAssignee;
    @BindView(R.id.txtAssignee)
    TextView txtAssignee;
    @BindView(R.id.icDescription)
    ImageView icDescription;
    @BindView(R.id.labelDescription)
    TextView labelDescription;
    @BindView(R.id.etDescription)
    EditText etDescription;
    @BindView(R.id.layoutDescriptionEmpty)
    LinearLayout layoutDescriptionEmpty;
    @BindView(R.id.icAttachments)
    ImageView icAttachments;
    @BindView(R.id.labelAttachments)
    TextView labelAttachments;
    @BindView(R.id.rvAttachments)
    RecyclerView rvAttachments;
    @BindView(R.id.nestedScrollView)
    DummyNestedScrollView nestedScrollView;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.cardAttachments)
    CardView cardAttachments;

    private boolean canManage;

    private Issue issue;

    private List<AttachmentsItem> attachmentItems = new ArrayList<>();
    private AttachmentsAdapter attachmentsAdapter = new AttachmentsAdapter(attachmentItems);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_issue;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);

        if (savedInstanceState == null) {
            canManage = getIntent().getBooleanExtra(EXTRA_CAN_MANAGE, false);
            issue = new Gson().fromJson(getIntent().getStringExtra(EXTRA_ISSUE), Issue.class);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (savedInstanceState == null) {
            init();
            updateIssue();
        } else {
            init();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEditMode(false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_ISSUE, new Gson().toJson(issue));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        issue = new Gson().fromJson(savedInstanceState.getString(EXTRA_ISSUE), Issue.class);
    }

    private void init() {
        Log.d(App.TAG, issue.toString());

        mainCollapsing.setTitle(issue.getShortDescription());
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

        etShortDescription.setText(issue.getShortDescription());
//        txtAuthor.setText(project.get);

        User creator = issue.getAuthor();
        if (creator != null) {
            txtAuthor.setText(creator.getRealName());
        }

        rvAttachments.setAdapter(attachmentsAdapter);
        rvAttachments.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = rvAttachments.getLayoutManager();
        layoutManager.setAutoMeasureEnabled(true);

        List<IssueAttachment> attachments = issue.getAttachments();
        if (attachments != null && !attachments.isEmpty()) {
            for (IssueAttachment attachment : attachments) {
                attachmentItems.add(new AttachmentsItem(attachment));
            }
        } else {
            cardAttachments.setVisibility(View.GONE);
        }
        attachmentsAdapter.notifyDataSetChanged();

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
            etShortDescription.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);

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

            etShortDescription.setFocusable(true);
            etShortDescription.setFocusableInTouchMode(true);
        } else {
            etShortDescription.setBackgroundTintMode(PorterDuff.Mode.CLEAR);

            mainToolbar.inflateMenu(R.menu.project_normal);
            behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                @Override
                public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                    return true;
                }
            });

            nestedScrollView.setOnTouchListener((View v, MotionEvent event) -> false);

            etShortDescription.setFocusable(false);
            etShortDescription.setFocusableInTouchMode(false);
        }
    }

    private void updateIssue() {
        api.getIssue(issue.getProject().getId(), issue.getIssueIndex())
           .compose(Rx.applySchedulers())
           .compose(bindToLifecycle())
           .doOnSubscribe(() -> progress.setVisibility(View.VISIBLE))
           .doOnNext(issue -> this.issue = issue)
           .doOnTerminate(() -> progress.setVisibility(View.GONE))
           .subscribe(issue -> init());
    }
}
