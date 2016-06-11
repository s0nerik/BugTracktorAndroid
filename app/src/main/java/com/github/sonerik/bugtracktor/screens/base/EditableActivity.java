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
import com.github.sonerik.bugtracktor.api.BugTracktorApi;
import com.github.sonerik.bugtracktor.bundlers.SerializableBundler;
import com.github.sonerik.bugtracktor.models.Permission;
import com.github.sonerik.bugtracktor.utils.Rx;
import com.google.common.collect.FluentIterable;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import icepick.State;
import rx.Observable;

/**
 * Created by sonerik on 6/7/16.
 */
public abstract class EditableActivity extends BaseActivity {
    public enum Mode { CREATE, EDIT, VIEW }

    @Inject
    protected BugTracktorApi api;

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
    protected boolean canManage;

    protected List<Permission> permissions;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (savedInstanceState == null) {
            startLoadingData();

            api.getPermissions(getProjectId())
               .compose(bindToLifecycle())
               .compose(Rx.applySchedulers())
               .subscribe(this::onPermissionsAcquired);
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

    protected void onPermissionsAcquired(List<Permission> permissions) {
        this.permissions = permissions;
        setMode(mode, false);
    }

    protected abstract Integer getProjectId();
    protected abstract Observable<?> loadData();
    protected abstract Observable<?> saveChanges();
    protected abstract void onModeChanged();

    protected final void startLoadingData() {
        loadData()
                .doOnSubscribe(() -> progress.setVisibility(View.VISIBLE))
                .doOnTerminate(() -> progress.setVisibility(View.GONE))
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(o -> init(), this::handleRequestError);
    }

    protected final void startSavingChanges() {
        saveChanges()
                .doOnSubscribe(() -> setMode(Mode.VIEW, false))
                .doOnSubscribe(() -> progress.setVisibility(View.VISIBLE))
                .doOnTerminate(() -> progress.setVisibility(View.GONE))
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(o -> init(), this::handleRequestError);
    }

    protected final boolean hasPermission(String permissionName) {
        if (permissions == null || permissionName == null)
            return false;
        else
            return FluentIterable.from(permissions).anyMatch(p -> permissionName.equals(p.getName()));
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
