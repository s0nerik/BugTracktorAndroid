package com.github.sonerik.bugtracktor.screens;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import com.github.sonerik.bugtracktor.adapters.attachments.AttachmentsAdapter;
import com.github.sonerik.bugtracktor.adapters.attachments.AttachmentsItem;
import com.github.sonerik.bugtracktor.api.BugTracktorApi;
import com.github.sonerik.bugtracktor.bundlers.ParcelBundler;
import com.github.sonerik.bugtracktor.events.EAttachmentClicked;
import com.github.sonerik.bugtracktor.events.EIssueChanged;
import com.github.sonerik.bugtracktor.events.EIssueTypeClicked;
import com.github.sonerik.bugtracktor.events.EProjectMemberClicked;
import com.github.sonerik.bugtracktor.models.Issue;
import com.github.sonerik.bugtracktor.models.IssueAttachment;
import com.github.sonerik.bugtracktor.models.IssueType;
import com.github.sonerik.bugtracktor.models.User;
import com.github.sonerik.bugtracktor.rx_adapter.BindableRxList;
import com.github.sonerik.bugtracktor.screens.base.EditableActivity;
import com.github.sonerik.bugtracktor.ui.views.DummyNestedScrollView;
import com.github.sonerik.bugtracktor.ui.views.TintableMenuToolbar;
import com.github.sonerik.bugtracktor.utils.EditTextUtils;
import com.github.sonerik.bugtracktor.utils.NamingUtils;
import com.github.sonerik.bugtracktor.utils.Rx;
import com.github.sonerik.bugtracktor.utils.RxBus;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.rits.cloning.Cloner;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.apache.commons.lang3.BooleanUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import icepick.State;
import rx.Observable;

/**
 * Created by sonerik on 6/2/16.
 */
public class IssueActivity extends EditableActivity {

    private static final int PICK_ATTACHMENT = 7865;

    @Inject
    BugTracktorApi api;

    @Inject
    User user;

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
    @BindView(R.id.txtType)
    TextView txtType;
    @BindView(R.id.layoutAttachmentsEmpty)
    LinearLayout layoutAttachmentsEmpty;
    @BindView(R.id.btnChangeStatus)
    Button btnChangeStatus;
    @BindView(R.id.txtStatus)
    TextView txtStatus;

    @InjectExtra
    @State(ParcelBundler.class)
    Issue issue;
    @State(ParcelBundler.class)
    Issue originalIssue;

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

        if (mode == Mode.CREATE) {
            issue.setAuthor(user);
        }

        originalIssue = new Cloner().deepClone(issue);

        initListeners();
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
        RxBus.on(EIssueTypeClicked.class)
             .compose(bindToLifecycle())
             .subscribe(e -> {
                 issue.setType(e.issueType);
                 init();
             });
        RxTextView.textChanges(etShortDescription)
                  .compose(bindToLifecycle())
                  .subscribe(text -> issue.setShortDescription(text.toString()));
        RxTextView.textChanges(etDescription)
                  .compose(bindToLifecycle())
                  .subscribe(text -> issue.setFullDescription(text.toString()));
    }

    @Override
    protected void init() {
        super.init();
        mainCollapsing.setTitle(issue.getShortDescription());
        etShortDescription.setText(issue.getShortDescription());
        etDescription.setText(issue.getFullDescription());

        User creator = issue.getAuthor();
        if (creator != null) {
            txtAuthor.setText(creator.getRealName());
        }

        IssueType type = issue.getType();
        if (type != null) {
            txtType.setText(NamingUtils.getReadableIssueType(type));
        } else {
            txtType.setText("Unassigned");
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
    }

    private void updateAttachments() {
        attachmentItems.clear();
        List<IssueAttachment> attachments = issue.getAttachments();
        if (attachments != null && !attachments.isEmpty()) {
            layoutAttachmentsEmpty.setVisibility(View.GONE);
            for (IssueAttachment attachment : attachments) {
                attachmentItems.add(new AttachmentsItem(attachment, canEdit()));
            }
        } else {
            layoutAttachmentsEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setMode(Mode mode, boolean expandToolbar) {
        super.setMode(mode, expandToolbar);
        icAssignee.setVisibility(canEdit() ? View.GONE : View.VISIBLE);
        icAddAttachment.setVisibility(canEdit() ? View.VISIBLE : View.GONE);
        icChangeAssignee.setVisibility(canEdit() ? View.VISIBLE : View.GONE);

        btnChangeStatus.setVisibility(canEdit() && mode != Mode.CREATE ? View.VISIBLE : View.GONE);
        btnChangeStatus.setText(BooleanUtils.isTrue(issue.getIsOpened()) ? "Close" : "Reopen");
        txtStatus.setText(BooleanUtils.isTrue(issue.getIsOpened()) ? "Opened" : "Closed");

        if (!canEdit() && Strings.isNullOrEmpty(issue.getFullDescription())) {
            layoutDescriptionEmpty.setVisibility(View.VISIBLE);
        } else {
            layoutDescriptionEmpty.setVisibility(View.GONE);
        }

        EditTextUtils.setEditingEnabled(etShortDescription, canEdit(), false);
        EditTextUtils.setEditingEnabled(etDescription, canEdit(), true);
    }

    @OnClick(R.id.btnAssignee)
    void onAssigneeClicked() {
        if (!canEdit()) return;
        startActivity(Henson.with(this)
                            .gotoSelectProjectMemberActivity()
                            .projectId(issue.getProject().getId())
                            .build());
    }

    @OnClick(R.id.btnType)
    void onTypeClicked() {
        if (!canEdit()) return;
        startActivity(
                Henson.with(this)
                      .gotoSelectIssueTypeActivity()
                      .projectId(issue.getProject().getId())
                      .build()
        );
    }

    @OnClick(R.id.icAddAttachment)
    void onAddAttachment() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_ATTACHMENT);
    }

    @OnClick(R.id.btnChangeStatus)
    void onChangeStatus() {
        issue.setIsOpened(!BooleanUtils.isTrue(issue.getIsOpened()));
        init();
    }

    @Override
    protected Observable loadData() {
        if (mode != Mode.CREATE)
            return api.getIssue(issue.getProject().getId(), issue.getIssueIndex())
                      .compose(Rx.applySchedulers())
                      .doOnNext(issue -> this.issue = issue);
        else
            return Observable.just(issue);
    }

    @Override
    protected Observable saveChanges() {
        if (mode == Mode.CREATE)
            return api.createIssue(issue.getProject().getId(), issue)
                      .compose(Rx.applySchedulers())
                      .doOnNext(issue -> this.issue = issue)
                      .doOnNext(issue -> RxBus.publish(new EIssueChanged(originalIssue, issue, EIssueChanged.Type.CREATED)));
        else
            return api.updateIssue(issue.getProject().getId(), issue.getIssueIndex(), issue)
                      .compose(Rx.applySchedulers())
                      .doOnNext(issue -> this.issue = issue)
                      .doOnNext(issue -> RxBus.publish(new EIssueChanged(originalIssue, issue, EIssueChanged.Type.UPDATED)));
    }

    @Override
    protected void onModeChanged() {
        updateAttachments();
    }

    @Override
    protected int getMenu(Mode mode) {
        switch (mode) {
            case CREATE:
                return R.menu.issue_edit;
            case EDIT:
                return R.menu.issue_edit;
            case VIEW:
                return R.menu.issue_normal;
            default:
                return R.menu.issue_normal;
        }
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
                          this::handleRequestError);
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
                attachmentItems.remove(new AttachmentsItem(e.attachment, canEdit()));
                break;
        }
    }

    private void onAttachmentSelected() {
        attachmentItems.add(new AttachmentsItem(null, canEdit()));
    }

    private void onAttachmentUploaded(IssueAttachment attachment) {
        layoutAttachmentsEmpty.setVisibility(View.GONE);

        attachmentItems.remove(new AttachmentsItem(null, canEdit()));
        attachmentItems.add(new AttachmentsItem(attachment, canEdit()));
    }
}
