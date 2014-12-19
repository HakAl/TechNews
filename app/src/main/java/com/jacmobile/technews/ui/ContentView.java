package com.jacmobile.technews.ui;

import android.app.Activity;
import android.view.ViewGroup;

/**
 * An indirection which allows controlling the root container used for each activity.
 */
public interface ContentView {
    /**
     * The root {@link android.view.ViewGroup} into which the activity should place its contents.
     */
    ViewGroup get(Activity activity);

    /**
     * An {@link com.jacmobile.technews.ui.ContentView} which returns the normal activity content view.
     */
    ContentView DEFAULT = new ContentView() {
        @Override
        public ViewGroup get(Activity activity) {
            return (ViewGroup) activity.findViewById(android.R.id.content);
        }
    };
}