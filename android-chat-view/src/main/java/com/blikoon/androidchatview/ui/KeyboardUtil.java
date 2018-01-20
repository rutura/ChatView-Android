package com.blikoon.androidchatview.ui;

import android.app.Activity;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by gakwaya on 2018/1/20.
 */

public class KeyboardUtil {

    /** Interface to be implemented by Activities interested in knowing whether the soft keyboard is opened
     * or closed.
     * They will then need to call KeyboardUtil.setKeyboardVisibilityListener(Activity activity,final KeyboardVisibilityListener keyboardVisibilityListener);
     *
     * An example of where this interface is used is in ListActivity*/
    public interface KeyboardVisibilityListener {
        void onKeyboardVisibilityChanged(boolean keyboardVisible);
    }

    public static void setKeyboardVisibilityListener(Activity activity, final KeyboardVisibilityListener keyboardVisibilityListener) {
        final View contentView = activity.findViewById(android.R.id.content);
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private int mPreviousHeight;

            @Override
            public void onGlobalLayout() {
                int newHeight = contentView.getHeight();
                if (mPreviousHeight != 0) {
                    if (mPreviousHeight > newHeight) {
                        // Height decreased: keyboard was shown
                        keyboardVisibilityListener.onKeyboardVisibilityChanged(true);
                    } else if (mPreviousHeight < newHeight) {
                        // Height increased: keyboard was hidden
                        keyboardVisibilityListener.onKeyboardVisibilityChanged(false);
                    } else {
                        // No change
                    }
                }
                mPreviousHeight = newHeight;
            }
        });
    }
}