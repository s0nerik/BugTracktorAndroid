package com.github.sonerik.bugtracktor.adapters.issue_types;

import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.s0nerik.rxbus.RxBus;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.events.EIssueTypeClicked;
import com.github.sonerik.bugtracktor.models.IssueType;
import com.github.sonerik.bugtracktor.utils.NamingUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.viewholders.FlexibleViewHolder;

public class IssueTypesViewHolder extends FlexibleViewHolder {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.subtitle)
    TextView subtitle;
    @BindView(R.id.layout)
    RelativeLayout layout;
    @BindView(R.id.btnOverflow)
    ImageView btnOverflow;

    public IssueTypesViewHolder(View view, FlexibleAdapter adapter) {
        super(view, adapter);
        ButterKnife.bind(this, view);
    }

    public void setIssueType(@NonNull IssueType issueType) {
        title.setText(NamingUtils.getReadableIssueType(issueType));
        subtitle.setText(issueType.getDescription());

        layout.setOnClickListener(v -> RxBus.post(new EIssueTypeClicked(issueType, EIssueTypeClicked.Type.ITEM)));
        btnOverflow.setOnClickListener(v -> {
            PopupMenu menu = new PopupMenu(btnOverflow.getContext(), btnOverflow, Gravity.RIGHT);
            menu.inflate(R.menu.edit_project_member);
            menu.setOnMenuItemClickListener(item -> {
                RxBus.post(new EIssueTypeClicked(issueType, EIssueTypeClicked.Type.REMOVE));
                return true;
            });
            menu.show();
        });
    }

    public void setEditMode(boolean editMode) {
        btnOverflow.setVisibility(editMode ? View.VISIBLE : View.GONE);
    }
}

