package com.jacmobile.technews.ui;

import android.app.Activity;
import android.app.Fragment;

/**
 * Created by alex on 12/13/14.
 */
public class DaggerFragment extends Fragment
{
    private boolean firstAttach = true;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if (firstAttach) {
            firstAttach = false;
            ((ABaseActivity) activity).inject(this);
        }
    }
}