package com.jacmobile.technews.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
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
    @SuppressWarnings("unused")
    public static final String WEBVIEW_FRAGMENT = "webview";
    public static final String NEWSLIST_FRAGMENT = "newslist";
    private LayoutInflater inflater;
    private RelativeLayout rootLayout;
    private ActionBarManager actionBarManager;
    private boolean isChild = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.inject(this);
        setContentView(R.layout.drawer_layout);

        this.inflater = LayoutInflater.from(this);
        this.rootLayout = (RelativeLayout) this.findViewById(R.id.container);
        actionBarManager = new ActionBarManager((Toolbar) this.findViewById(R.id.toolbar));

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new DaggerListFragment.TestListFragment(), NEWSLIST_FRAGMENT)
                    .commit();
        }
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
        else {
            if (isChild) {
                isChild = false;
                this.newFragment(NEWSLIST_FRAGMENT);
            } else {
                super.onBackPressed();
            }
        }
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

    public void setWindowTitle(String title)
    {
        this.actionBarManager.setActionBarTitle(title);
    }

    public void newFragment(String tag, String ... args)
    {
        isChild = true;
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        final Fragment fragment;
        try {
            switch (tag) {
                case WEBVIEW_FRAGMENT:
                    fragment = WebViewFragment.newInstance(args[0], args[1]);
                    if (fragmentManager.findFragmentByTag(WEBVIEW_FRAGMENT) != null)
                        transaction.addToBackStack(null);
                    transaction.replace(R.id.container, fragment, WEBVIEW_FRAGMENT);
                    break;
                case NEWSLIST_FRAGMENT:
                    fragment = new DaggerListFragment.TestListFragment();
                    if (fragmentManager.findFragmentByTag(NEWSLIST_FRAGMENT) != null)
                        transaction.addToBackStack(null);
                    transaction.replace(R.id.container, fragment, NEWSLIST_FRAGMENT);
                    break;
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void launchWebFragment(String title, String url) {
//        final FragmentManager fragmentManager = getFragmentManager();
//
//        // Close the drawer
//        actionBarManager.closeDrawer();
//
//        final FragmentTransaction transaction = fragmentManager.beginTransaction();
//        try {
//            final WebViewFragment fragment = WebViewFragment.newInstance(title, url);
//
//            if (fragmentManager.findFragmentByTag(WEBVIEW_FRAGMENT) != null)
//                transaction.addToBackStack(null);
//
//            transaction.replace(R.id.container, fragment, WEBVIEW_FRAGMENT);
//            transaction.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
