package com.github.sonerik.bugtracktor.screens.base;

import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.f2prateek.dart.InjectExtra;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.bundlers.SerializableBundler;
import com.github.sonerik.bugtracktor.utils.Rx;

import butterknife.BindView;
import icepick.State;
import rx.Observable;

/**
 * Created by sonerik on 6/7/16.
 */
public abstract class EditableActivity extends BaseActivity {
    public enum Mode { CREATE, EDIT, VIEW }

    @BindView(R.id.progress)
    View progress;
    @BindView(R.id.mainToolbar)
    Toolbar mainToolbar;
    @BindView(R.id.mainAppbar)
    AppBarLayout mainAppbar;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    @InjectExtra
    @State(SerializableBundler.class)
    protected Mode mode = Mode.VIEW;

    @InjectExtra
    @State
    boolean canManage;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (savedInstanceState == null) {
            init();
            startLoadingData();
        } else {
            init();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    protected void init() {
        mainToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mainToolbar.setNavigationOnClickListener(v -> finish());
        mainToolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.edit:
                    setMode(Mode.EDIT, true);
                    break;
                case R.id.save:
                    startSavingChanges();
                    break;
            }
            return true;
        });

        setMode(mode, false);
    }

    protected abstract Observable<?> loadData();
    protected abstract Observable<?> saveChanges();
    protected abstract void onModeChanged();

    protected final void startLoadingData() {
        loadData()
                .compose(Rx.applySchedulers())
                .compose(bindToLifecycle())
                .doOnSubscribe(() -> progress.setVisibility(View.VISIBLE))
                .doOnTerminate(() -> progress.setVisibility(View.GONE))
                .subscribe(o -> init(), this::handleRequestError);
    }

    protected final void startSavingChanges() {
        saveChanges()
                .compose(bindToLifecycle())
                .compose(Rx.applySchedulers())
                .doOnSubscribe(() -> setMode(Mode.VIEW, false))
                .doOnSubscribe(() -> progress.setVisibility(View.VISIBLE))
                .doOnTerminate(() -> progress.setVisibility(View.GONE))
                .subscribe(o -> init(), this::handleRequestError);
    }

    @MenuRes
    protected abstract int getMenu(Mode mode);

    protected final boolean canEdit() {
        return (mode == Mode.EDIT && canManage) || mode == Mode.CREATE;
    }

    protected void setMode(Mode mode, boolean expandToolbar) {
        if (mode == Mode.EDIT && !canManage) return;
        this.mode = mode;

        mainToolbar.getMenu().clear();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mainAppbar.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();

        if (behavior == null) {
            behavior = new AppBarLayout.Behavior();
            params.setBehavior(behavior);
        }

        mainToolbar.inflateMenu(getMenu(mode));
        if (canEdit() && expandToolbar) {
            mainAppbar.setExpanded(true, true);
            nestedScrollView.fullScroll(View.FOCUS_UP);
        }

        onModeChanged();
    }
}
