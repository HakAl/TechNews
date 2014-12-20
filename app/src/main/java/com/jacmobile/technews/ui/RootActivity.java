package com.jacmobile.technews.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

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
    private ActionBarManager actionBarManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.inject(this);
        this.inflater = LayoutInflater.from(this);
//        DrawerLayout drawerLayout = (DrawerLayout) inflater.inflate(R.layout.drawer_layout, contentView.get(this), false);
        setContentView(R.layout.drawer_layout);

        this.rootLayout = (RelativeLayout) this.findViewById(R.id.container);
        actionBarManager = new ActionBarManager((Toolbar) this.findViewById(R.id.toolbar));
        actionBarManager.onCreate();
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new DaggerListFragment.TestListFragment(), getClass().getSimpleName())
                    .commit();
        }


//        this.rootLayout = (RelativeLayout) inflater.inflate(R.layout.root, contentView.get(this), false);
//        this.setContentView(rootLayout);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        actionBarManager.onPostCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        actionBarManager.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed()
    {
        if (actionBarManager.isDrawerOpen()) {
            actionBarManager.onStop();
        }
        //HACKY THINGY
//        else {
//            if (isChild) {
//                isChild = false;
//                this.newSensortList();
//            } else {
//                super.onBackPressed();
//            }
//        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        actionBarManager.onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        actionBarManager = null;
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
}
