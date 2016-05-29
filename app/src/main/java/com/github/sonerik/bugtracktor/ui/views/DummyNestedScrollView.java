package com.github.sonerik.bugtracktor.ui.views;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class DummyNestedScrollView extends NestedScrollView {
public DummyNestedScrollView(Context context) {
    super(context);
}

public DummyNestedScrollView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
}

public DummyNestedScrollView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
}

/**
 * Fixind problem with recyclerView in nested scrollview requesting focus
 * http://stackoverflow.com/questions/36314836/recycler-view-inside-nestedscrollview-causes-scroll-to-start-in-the-middle
 * @param child
 * @param focused
 */
@Override
public void requestChildFocus(View child, View focused) {
    Log.d(getClass().getSimpleName(), "Request focus");
    //super.requestChildFocus(child, focused);

}


/**
 * http://stackoverflow.com/questions/36314836/recycler-view-inside-nestedscrollview-causes-scroll-to-start-in-the-middle
 * @param direction
 * @param previouslyFocusedRect
 * @return
 */
@Override
protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
    Log.d(getClass().getSimpleName(), "Request focus descendants");
    //return super.onRequestFocusInDescendants(direction, previouslyFocusedRect);
    return false;
}
}