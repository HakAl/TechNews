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

/**
 * Created by alex on 12/15/14.
 */
public class RootActivity extends ABaseActivity implements DrawerClickListener
{
    @SuppressWarnings("unused")
    public static final String WEBVIEW_FRAGMENT = "webview";
    public static final String NEWSLIST_FRAGMENT = "newslist";
    public static final String NEWS_FEED_LIST_FRAGMENT = "newsFeedlist";
    private LayoutInflater inflater;
    private RelativeLayout rootLayout;
    private ActionBarManager actionBarManager;
    private Fragment newsListFragment = null;
    private boolean isChild = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.inject(this);
        this.setContentView(R.layout.drawer_layout);

        this.inflater = LayoutInflater.from(this);
        this.rootLayout = (RelativeLayout) this.findViewById(R.id.container);
        actionBarManager = new ActionBarManager((Toolbar) this.findViewById(R.id.toolbar));

        if (savedInstanceState == null) {
            initNewsList();
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
    public void navListClick(int position)
    {
        //TODO
    }

    public void setWindowTitle(String title)
    {
        this.actionBarManager.setActionBarTitle(title);
    }

    public void newFragment(String tag, String... args)
    {
        isChild = true;
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        final Fragment fragment;
        try {
            switch (tag) {
                case NEWSLIST_FRAGMENT:
                    transaction.replace(R.id.container, newsListFragment, NEWSLIST_FRAGMENT);
                    break;
                case WEBVIEW_FRAGMENT:
                    fragment = WebViewFragment.newInstance(args[0], args[1]);
                    if (fragmentManager.findFragmentByTag(WEBVIEW_FRAGMENT) != null)
                        transaction.addToBackStack(null);
                    transaction.replace(R.id.container, fragment, WEBVIEW_FRAGMENT);
                    break;
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initNewsList()
    {
        try {
            newsListFragment = NewsListFragment.newInstance("TechCrunch", "http://www.forbes.com/technology/feed/", 0);
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, newsListFragment, NEWSLIST_FRAGMENT)
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}