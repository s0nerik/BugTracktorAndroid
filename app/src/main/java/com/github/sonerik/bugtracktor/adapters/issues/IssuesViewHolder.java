package com.github.sonerik.bugtracktor.adapters.issues;

import android.view.View;
import android.widget.TextView;

import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.models.Issue;

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
    @BindView(R.id.layout)
    View layout;

    public IssuesViewHolder(View view, FlexibleAdapter adapter) {
        super(view, adapter);
        ButterKnife.bind(this, view);
    }

    public void setIssue(Issue issue) {
        val creationDate = issue.getCreationDate();
        if (creationDate != null)
            date.setText(creationDate.toString());

        val description = issue.getShortDescription();
        if (description != null)
            if (issue.getIssueIndex() != null)
                title.setText("#"+issue.getIssueIndex()+" "+description);
            else
                title.setText(description);

        val author = issue.getAuthor();
        if (author != null)
            subtitle.setText(author.getRealName());
    }
}

