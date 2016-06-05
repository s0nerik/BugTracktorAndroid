package com.github.sonerik.bugtracktor.utils;

import android.graphics.PorterDuff;
import android.text.InputType;
import android.widget.EditText;

/**
 * Created by sonerik on 6/5/16.
 */
public class EditTextUtils {
    public static void setEditingEnabled(EditText et, boolean state, boolean multiLine) {
        et.setFocusable(state);
        et.setFocusableInTouchMode(state);
        et.setInputType(getInputType(state, multiLine));
        et.setBackgroundTintMode(state ? PorterDuff.Mode.SRC_ATOP : PorterDuff.Mode.CLEAR);
        if (multiLine) {
            et.setHorizontallyScrolling(false);
            et.setMaxLines(Integer.MAX_VALUE);
        }
    }

    private static int getInputType(boolean state, boolean multiLine) {
        int result;
        if (state) {
            result = InputType.TYPE_TEXT_FLAG_AUTO_CORRECT | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE;
        } else {
            result = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;
        }

        if (multiLine) {
            result = result | InputType.TYPE_TEXT_FLAG_MULTI_LINE;
        }

        return result;
    }
}
