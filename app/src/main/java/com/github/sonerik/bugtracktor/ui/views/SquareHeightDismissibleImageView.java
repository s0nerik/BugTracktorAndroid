package com.github.sonerik.bugtracktor.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.dmallcott.dismissibleimageview.DismissibleImageView;

/**
 * Created by Alex on 6/1/2016.
 */
public class SquareHeightDismissibleImageView extends DismissibleImageView {
    public SquareHeightDismissibleImageView(Context context) {
        super(context);
    }

    public SquareHeightDismissibleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareHeightDismissibleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}
