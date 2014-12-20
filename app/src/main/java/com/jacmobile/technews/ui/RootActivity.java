package com.jacmobile.technews.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jacmobile.technews.R;

import javax.inject.Inject;

/**
 * Created by alex on 12/15/14.
 */
public class RootActivity extends ABaseActivity
{
    @Inject ContentView contentView;
    private LayoutInflater inflater;
    private RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.inject(this);
        this.inflater = LayoutInflater.from(this);
        this.rootLayout = (RelativeLayout) inflater.inflate(R.layout.root, contentView.get(this), false);
        this.setContentView(rootLayout);


        getFragmentManager()
                .beginTransaction()
                .add(R.id.root, new DaggerListFragment.TestListFragment(), getClass().getSimpleName())
                .commit();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
