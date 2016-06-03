package com.github.sonerik.bugtracktor.screens.issue;

import android.Manifest;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.github.sonerik.bugtracktor.bundlers.IssueBundler;
import com.github.sonerik.bugtracktor.events.EAttachmentClicked;
import com.github.sonerik.bugtracktor.models.Issue;
import com.github.sonerik.bugtracktor.models.IssueAttachment;
import com.github.sonerik.bugtracktor.models.User;
import com.github.sonerik.bugtracktor.screens.base.BaseActivity;
import com.github.sonerik.bugtracktor.ui.views.DummyNestedScrollView;
import com.github.sonerik.bugtracktor.ui.views.TintableMenuToolbar;
import com.github.sonerik.bugtracktor.utils.Rx;
import com.github.sonerik.bugtracktor.utils.RxBus;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import icepick.Icepick;
import icepick.State;
import io.github.kobakei.grenade.annotation.Extra;
import io.github.kobakei.grenade.annotation.Navigator;

/**
 * Created by sonerik on 6/2/16.
 */
@Navigator
public class IssueActivity extends BaseActivity {

//    public static final String EXTRA_CAN_MANAGE = "EXTRA_CAN_MANAGE";
//    public static final String EXTRA_ISSUE = "EXTRA_ISSUE";

    private static final int PICK_ATTACHMENT = 7865;

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
    @BindView(R.id.icAddAttachment)
    ImageView icAddAttachment;

    @State
    boolean editMode = false;
    @Extra
    @State
    boolean canManage;
    @Extra
    @State(IssueBundler.class)
    Issue issue;

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
        IssueActivityNavigator.inject(this, getIntent());
//        Icepick.restoreInstanceState(this, getIntent().getExtras());
//
//        if (savedInstanceState == null) {
//            canManage = getIntent().getBooleanExtra(EXTRA_CAN_MANAGE, false);
//            issue = new Gson().fromJson(getIntent().getStringExtra(EXTRA_ISSUE), Issue.class);
//        }
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
        init();
        initListeners();
    }

    private void initListeners() {
        RxBus.on(EAttachmentClicked.class)
             .compose(bindToLifecycle())
             .subscribe(this::onAttachmentClicked);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
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
                    saveIssueChanges();
                    break;
            }
            return true;
        });

        etShortDescription.setText(issue.getShortDescription());
        etShortDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                issue.setShortDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        etDescription.setText(issue.getFullDescription());
        etDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                issue.setFullDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        User creator = issue.getAuthor();
        if (creator != null) {
            txtAuthor.setText(creator.getRealName());
        }

        List<User> assignees = issue.getAssignees();
        if (assignees != null && !assignees.isEmpty()) {
            txtAssignee.setText(assignees.get(0).getRealName());
        } else {
            txtAssignee.setText("None");
        }

        rvAttachments.setAdapter(attachmentsAdapter);
        rvAttachments.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = rvAttachments.getLayoutManager();
        layoutManager.setAutoMeasureEnabled(true);

        updateAttachments();

        setEditMode(editMode);
    }

    private void updateAttachments() {
        attachmentItems.clear();
        List<IssueAttachment> attachments = issue.getAttachments();
        if (attachments != null && !attachments.isEmpty()) {
            cardAttachments.setVisibility(View.VISIBLE);
            for (IssueAttachment attachment : attachments) {
                attachmentItems.add(new AttachmentsItem(attachment));
            }
        } else {
            cardAttachments.setVisibility(View.GONE);
        }
        attachmentsAdapter.notifyDataSetChanged();
    }

    private void setEditMode(boolean state) {
        if (!canManage) state = false;
        editMode = state;

        mainToolbar.getMenu().clear();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mainAppbar.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();

        if (behavior == null) {
            behavior = new AppBarLayout.Behavior();
            params.setBehavior(behavior);
        }

        if (state) {
            etShortDescription.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
            etDescription.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);

            mainToolbar.inflateMenu(R.menu.issue_edit);
            mainAppbar.setExpanded(true, true);
            nestedScrollView.fullScroll(View.FOCUS_UP);


            etShortDescription.setFocusable(true);
            etShortDescription.setFocusableInTouchMode(true);
            etDescription.setFocusable(true);
            etDescription.setFocusableInTouchMode(true);

            icAddAttachment.setVisibility(View.VISIBLE);
            icAssignee.setVisibility(View.GONE);
            icChangeAssignee.setVisibility(View.VISIBLE);
        } else {
            etShortDescription.setBackgroundTintMode(PorterDuff.Mode.CLEAR);
            etDescription.setBackgroundTintMode(PorterDuff.Mode.CLEAR);

            mainToolbar.inflateMenu(R.menu.issue_normal);

            etShortDescription.setFocusable(false);
            etShortDescription.setFocusableInTouchMode(false);
            etDescription.setFocusable(false);
            etDescription.setFocusableInTouchMode(false);

            icAddAttachment.setVisibility(View.GONE);
            icAssignee.setVisibility(View.VISIBLE);
            icChangeAssignee.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.icAddAttachment)
    void onAddAttachment() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_ATTACHMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_ATTACHMENT && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            RxPermissions.getInstance(this)
                         .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                         .filter(granted -> granted)
                         .compose(bindToLifecycle())
                         .subscribe(granted -> addAttachmentByUri(selectedImageUri));
        }
    }

    private void addAttachmentByUri(Uri uri) {
        try {
            InputStream img = getContentResolver().openInputStream(uri);
            api.addAttachment(issue, img)
               .compose(bindToLifecycle())
               .compose(Rx.applySchedulers())
               .doOnSubscribe(this::onAttachmentSelected)
               .subscribe(this::onAttachmentUploaded,
                          throwable -> Log.e(App.TAG, "Error uploading image: "+throwable));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void onAttachmentClicked(EAttachmentClicked e) {
        switch (e.type) {
            case ITEM:
                break;
            case REMOVE:
                issue.getAttachments().remove(e.attachment);
                updateAttachments();
                break;
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

    private void saveIssueChanges() {
        api.updateIssue(issue.getProject().getId(), issue.getIssueIndex(), issue)
           .compose(Rx.applySchedulers())
           .compose(bindToLifecycle())
           .doOnSubscribe(() -> setEditMode(false))
           .doOnSubscribe(() -> progress.setVisibility(View.VISIBLE))
           .doOnNext(issue -> this.issue = issue)
           .doOnTerminate(() -> progress.setVisibility(View.GONE))
           .subscribe(issue -> init());
    }

    private void onAttachmentSelected() {
        issue.getAttachments().add(null);
    }

    private void onAttachmentUploaded(String url) {
        issue.getAttachments().remove(null);
        updateAttachments();
    }
}
