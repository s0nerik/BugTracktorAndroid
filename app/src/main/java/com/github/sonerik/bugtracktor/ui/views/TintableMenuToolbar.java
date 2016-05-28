package com.github.sonerik.bugtracktor.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;

import com.github.sonerik.bugtracktor.R;

public class TintableMenuToolbar extends Toolbar {

    private int menuItemTintColor;

    public TintableMenuToolbar(Context context) {
        super(context);
    }

    public TintableMenuToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TintableMenuToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        Context ctx = getContext();
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.TintableMenuToolbar);
        try {
            menuItemTintColor = a.getColor(R.styleable.TintableMenuToolbar_menuItemTint, Color.BLACK);
        } finally {
            a.recycle();
        }
    }

    @Override
    public void inflateMenu(@MenuRes int resId) {
        super.inflateMenu(resId);
        Menu menu = getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            Drawable icon = item.getIcon();
            if (icon != null) {
                item.setIcon(applyTint(icon));
            }
        }
    }

    private Drawable applyTint(Drawable icon){
        icon.setColorFilter(
                new PorterDuffColorFilter(menuItemTintColor, PorterDuff.Mode.SRC_IN)
        );
        return icon;
    }
}
