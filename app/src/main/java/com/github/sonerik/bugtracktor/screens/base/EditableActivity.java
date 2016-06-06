package com.github.sonerik.bugtracktor.screens.base;

import android.support.annotation.MenuRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.f2prateek.dart.InjectExtra;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.bundlers.SerializableBundler;

import butterknife.BindView;
import icepick.State;

/**
 * Created by sonerik on 6/7/16.
 */
public abstract class EditableActivity extends BaseActivity {
    public enum Mode { CREATE, EDIT, VIEW }

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

    @MenuRes
    protected abstract int getMenu(Mode mode);

    protected boolean canEdit() {
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
    }
}
