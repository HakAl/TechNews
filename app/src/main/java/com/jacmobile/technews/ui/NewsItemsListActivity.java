package com.jacmobile.technews.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.jacmobile.technews.R;
import com.jacmobile.technews.networking.NetworkModule;
import com.jacmobile.technews.networking.NewsEntity;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;


/**
 * An activity representing a list of NewsItems. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link NewsItemsDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link NewsItemsListFragment} and the item details
 * (if present) is a {@link NewsItemsDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link NewsItemsListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class NewsItemsListActivity extends ABaseActivity implements NewsItemsListFragment.Callbacks
{
    @Inject Bus bus;
    @Inject NetworkModule networkModule;

    @Subscribe public void busEvent(NewsEntity output)
    {
        Log.wtf("News Feed: ", output.getRaw());
    }

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsitems_list);

        if (findViewById(R.id.newsitems_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((NewsItemsListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.newsitems_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    @Override protected void onStart()
    {
        super.onStart();
        bus.register(this);
        networkModule.get("http://feeds.wired.com/wired/index");
    }

    @Override protected void onStop()
    {
        super.onStop();
        bus.unregister(this);
    }

    /**
     * Callback method from {@link NewsItemsListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id)
    {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(NewsItemsDetailFragment.ARG_ITEM_ID, id);
            NewsItemsDetailFragment fragment = new NewsItemsDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.newsitems_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, NewsItemsDetailActivity.class);
            detailIntent.putExtra(NewsItemsDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
