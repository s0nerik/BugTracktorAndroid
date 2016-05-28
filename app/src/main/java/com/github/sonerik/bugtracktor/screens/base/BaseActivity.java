package com.github.sonerik.bugtracktor.screens.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by sonerik on 5/28/16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected CompositeSubscription sub = new CompositeSubscription();

    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        sub.clear();
        super.onDestroy();
    }
}
