package com.github.sonerik.bugtracktor.adapters.issues;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.TextView;

import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.events.EIssueClicked;
import com.github.sonerik.bugtracktor.models.Issue;
import com.github.sonerik.bugtracktor.utils.RxBus;

import org.apache.commons.lang3.BooleanUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.viewholders.FlexibleViewHolder;
import lombok.val;

public class IssuesViewHolder extends FlexibleViewHolder {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.subtitle)
    TextView subtitle;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.layout)
    View layout;

    public IssuesViewHolder(View view, FlexibleAdapter adapter) {
        super(view, adapter);
        ButterKnife.bind(this, view);
    }

    public void setIssue(Issue issue) {
        val description = issue.getShortDescription();
        if (description != null)
            if (issue.getIssueIndex() != null)
                title.setText("#"+issue.getIssueIndex()+" "+description);
            else
                title.setText(description);

        val author = issue.getAuthor();
        if (author != null)
            subtitle.setText("@"+author.getNickname());

        val creationDate = issue.getCreationDate();
        if (creationDate != null)
            date.setText(creationDate.toString());

        status.setText(BooleanUtils.isTrue(issue.getIsOpened()) ? "Opened" : "Closed");
        status.setBackgroundTintList(
                ColorStateList.valueOf(
                        status.getContext()
                              .getResources()
                              .getColor(BooleanUtils.isTrue(issue.getIsOpened()) ? R.color.md_green_400 : R.color.md_red_400)
                )
        );

        layout.setOnClickListener(v -> RxBus.publish(new EIssueClicked(issue)));
    }
}

