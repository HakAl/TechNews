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
import com.jacmobile.technews.networking.RssUrls;
import com.jacmobile.technews.networking.rss.channels.Channel;

import javax.inject.Inject;

/**
 * Created by alex on 12/15/14.
 */
public class RootActivity extends ABaseActivity implements DrawerClickListener, FeedListObserver
{
    @SuppressWarnings("unused")
    public static final String WEBVIEW_FRAGMENT = "webview";
    public static final String NEWSLIST_FRAGMENT = "newslist";
    public static final String FEED_LIST_FRAGMENT = "feedlist";

    private LayoutInflater inflater;
    private RelativeLayout rootLayout;
    @Inject ActionBarManager actionBarManager;
    private Fragment newsListFragment = null;
    private Fragment feedListFragment = null;
    private Channel currentChannel = null;
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
            initFeedList();
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
                getFragmentManager().beginTransaction().hide(newsListFragment).commit();
                getFragmentManager().beginTransaction().show(feedListFragment).commit();
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
    public void drawerListClick(int position)
    {
        //TODO
    }

    //    FeedListObserver
    @Override
    public void onItemClick(int position)
    {
        this.currentChannel = RssUrls.getChannels()[position];
        this.newsList(currentChannel);
    }

    public void setWindowTitle(String title)
    {
        this.actionBarManager.setActionBarTitle(title);
    }

    public void showFragment(String tag, String... args)
    {
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        final Fragment fragment;


        try {
            if (fragmentManager.findFragmentByTag(tag) == null) {
                switch (tag) {
                    case FEED_LIST_FRAGMENT:
                        transaction.replace(R.id.container, feedListFragment, NEWSLIST_FRAGMENT);
                        break;
                    case WEBVIEW_FRAGMENT:
                        fragment = WebViewFragment.newInstance(args[0], args[1]);
                        if (fragmentManager.findFragmentByTag(WEBVIEW_FRAGMENT) != null)
                            transaction.addToBackStack(null);
                        transaction.replace(R.id.container, fragment, WEBVIEW_FRAGMENT);
                        break;
                }
                transaction.commit();
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //TODO WIP
//
//    private Fragment getFragmentTag(String tag)
//    {
//        return (getFragmentManager().findFragmentByTag(tag) == null) ?
//                newFragment(tag)
//                : getFragmentManager().findFragmentByTag(tag);
//    }
//
//    private Fragment newFragment(String tag)
//    {
//
//    }

    private void initFeedList()
    {
        try {
            feedListFragment = FeedListFragment.newInstance(0);
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, feedListFragment, FEED_LIST_FRAGMENT)
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void newsList(Channel channel)
    {
        isChild = true;
        newsListFragment = NewsListFragment.newInstance(channel.getTitle(), channel.getLink(), 0);
        getFragmentManager().beginTransaction().hide(feedListFragment).commit();
        getFragmentManager().beginTransaction()
                .add(R.id.container, newsListFragment, NEWSLIST_FRAGMENT)
                .commit();
    }
}