package com.github.sonerik.bugtracktor.screens.create_project;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.github.sonerik.bugtracktor.App;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.api.BugTracktorApi;
import com.github.sonerik.bugtracktor.screens.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by sonerik on 5/29/16.
 */
public class CreateProjectActivity extends BaseActivity {

    @Inject
    BugTracktorApi api;

    @BindView(R.id.name)
    AppCompatEditText name;
    @BindView(R.id.nameInputLayout)
    TextInputLayout nameInputLayout;
    @BindView(R.id.shortDescription)
    AppCompatEditText shortDescription;
    @BindView(R.id.shortDescriptionInputLayout)
    TextInputLayout shortDescriptionInputLayout;
    @BindView(R.id.fullDescription)
    AppCompatEditText fullDescription;
    @BindView(R.id.fullDescriptionInputLayout)
    TextInputLayout fullDescriptionInputLayout;
    @BindView(R.id.editLayout)
    LinearLayout editLayout;
    @BindView(R.id.btnCancel)
    AppCompatButton btnCancel;
    @BindView(R.id.btnCreate)
    AppCompatButton btnCreate;
    @BindView(R.id.progress)
    ProgressBar progress;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_project;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
    }

    @OnClick(R.id.btnCreate)
    void onCreateClicked() {
        progress.setVisibility(View.VISIBLE);
        btnCancel.setVisibility(View.GONE);
        btnCreate.setVisibility(View.GONE);
        editLayout.setVisibility(View.GONE);
        new Handler().postDelayed(this::finish, 2000);
    }

    @OnClick(R.id.btnCancel)
    void onCancelClicked() {
        finish();
    }

}
