package com.github.sonerik.bugtracktor.screens.issue;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import com.github.sonerik.bugtracktor.bundlers.ParcelBundler;
import com.github.sonerik.bugtracktor.events.EAttachmentClicked;
import com.github.sonerik.bugtracktor.events.EProjectMemberClicked;
import com.github.sonerik.bugtracktor.models.Issue;
import com.github.sonerik.bugtracktor.models.IssueAttachment;
import com.github.sonerik.bugtracktor.models.User;
import com.github.sonerik.bugtracktor.rx_adapter.BindableRxList;
import com.github.sonerik.bugtracktor.screens.base.BaseActivity;
import com.github.sonerik.bugtracktor.screens.project_members.SelectProjectMemberActivityNavigator;
import com.github.sonerik.bugtracktor.ui.views.DummyNestedScrollView;
import com.github.sonerik.bugtracktor.ui.views.TintableMenuToolbar;
import com.github.sonerik.bugtracktor.utils.EditTextUtils;
import com.github.sonerik.bugtracktor.utils.Rx;
import com.github.sonerik.bugtracktor.utils.RxBus;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import icepick.State;
import io.github.kobakei.grenade.annotation.Extra;
import io.github.kobakei.grenade.annotation.Navigator;
import ru.noties.debug.Debug;

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
    @State(ParcelBundler.class)
    Issue issue;

    private BindableRxList<AttachmentsItem> attachmentItems = new BindableRxList<>();
    private AttachmentsAdapter attachmentsAdapter = new AttachmentsAdapter(attachmentItems);

    @Override
    protected Map<BindableRxList, RecyclerView.Adapter> getBindableLists() {
        return ImmutableMap.of(attachmentItems, attachmentsAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_issue;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        IssueActivityNavigator.inject(this, getIntent());
        initListeners();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (savedInstanceState == null) {
            init();
            loadIssueData();
        } else {
            init();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void initListeners() {
        RxBus.on(EAttachmentClicked.class)
             .compose(bindToLifecycle())
             .subscribe(this::onAttachmentClicked);
        RxBus.on(EProjectMemberClicked.class)
             .compose(bindToLifecycle())
             .subscribe(e -> {
                 issue.setAssignees(Lists.newArrayList(e.member.getUser()));
                 init();
             });
        RxTextView.textChanges(etShortDescription)
                  .compose(bindToLifecycle())
                  .subscribe(text -> issue.setShortDescription(text.toString()));
        RxTextView.textChanges(etDescription)
                  .compose(bindToLifecycle())
                  .subscribe(text -> issue.setFullDescription(text.toString()));
    }

    private void init() {
        Debug.d("Init:\n"+issue);

        mainCollapsing.setTitle(issue.getShortDescription());
        mainToolbar.inflateMenu(R.menu.project_normal);
        mainToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mainToolbar.setNavigationOnClickListener(v -> finish());
        mainToolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.edit:
                    setEditMode(true, true);
                    updateAttachments();
                    break;
                case R.id.save:
                    saveChanges();
                    break;
            }
            return true;
        });

        etShortDescription.setText(issue.getShortDescription());
        etDescription.setText(issue.getFullDescription());

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

        setEditMode(editMode, false);

        updateAttachments();
    }

    private void updateAttachments() {
        attachmentItems.clear();
        List<IssueAttachment> attachments = issue.getAttachments();
        if (attachments != null && !attachments.isEmpty()) {
            cardAttachments.setVisibility(View.VISIBLE);
            for (IssueAttachment attachment : attachments) {
                attachmentItems.add(new AttachmentsItem(attachment, editMode));
            }
        } else {
            cardAttachments.setVisibility(View.GONE);
        }
    }

    private void setEditMode(boolean state, boolean expandToolbar) {
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
            mainToolbar.inflateMenu(R.menu.issue_edit);
            if (expandToolbar) {
                mainAppbar.setExpanded(true, true);
                nestedScrollView.fullScroll(View.FOCUS_UP);
            }

            icAddAttachment.setVisibility(View.VISIBLE);
            icAssignee.setVisibility(View.GONE);
            icChangeAssignee.setVisibility(View.VISIBLE);
        } else {
            mainToolbar.inflateMenu(R.menu.issue_normal);

            icAddAttachment.setVisibility(View.GONE);
            icAssignee.setVisibility(View.VISIBLE);
            icChangeAssignee.setVisibility(View.GONE);
        }

        EditTextUtils.setEditingEnabled(etShortDescription, state, false);
        EditTextUtils.setEditingEnabled(etDescription, state, true);
    }

    @OnClick(R.id.btnAssignee)
    void onAssigneeClicked() {
        if (!editMode) return;
        startActivity(new SelectProjectMemberActivityNavigator(issue.getProject().getId()).build(this));
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
                attachmentItems.remove(new AttachmentsItem(e.attachment, editMode));
                break;
        }
    }

    private void loadIssueData() {
        api.getIssue(issue.getProject().getId(), issue.getIssueIndex())
           .compose(bindToLifecycle())
           .compose(Rx.applySchedulers())
           .doOnSubscribe(() -> progress.setVisibility(View.VISIBLE))
           .doOnNext(issue -> this.issue = issue)
           .doOnTerminate(() -> progress.setVisibility(View.GONE))
           .subscribe(issue -> init());
    }

    private void saveChanges() {
        api.updateIssue(issue.getProject().getId(), issue.getIssueIndex(), issue)
           .compose(bindToLifecycle())
           .compose(Rx.applySchedulers())
           .doOnSubscribe(() -> setEditMode(false, false))
           .doOnSubscribe(() -> progress.setVisibility(View.VISIBLE))
           .doOnNext(issue -> this.issue = issue)
           .doOnTerminate(() -> progress.setVisibility(View.GONE))
           .subscribe(issue -> init());
    }

    private void onAttachmentSelected() {
        attachmentItems.add(new AttachmentsItem(null, editMode));
    }

    private void onAttachmentUploaded(IssueAttachment attachment) {
        attachmentItems.remove(new AttachmentsItem(null, editMode));
        attachmentItems.add(new AttachmentsItem(attachment, editMode));
    }
}
