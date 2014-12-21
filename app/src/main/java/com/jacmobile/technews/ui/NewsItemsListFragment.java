package com.jacmobile.technews.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.jacmobile.technews.networking.NetworkModule;
import com.jacmobile.technews.networking.rss.entities.NewsEntity;
import com.jacmobile.technews.networking.rss.entities.NewsItem;
import com.jacmobile.technews.networking.RssHandler;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.xml.parsers.SAXParserFactory;

/**
 * A list fragment representing a list of NewsItems. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link NewsItemsDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class NewsItemsListFragment extends DaggerListFragment
{
    @Inject Bus bus;
    @Inject NetworkModule networkModule;
    private ArrayList<NewsItem> rssFeed = null;

    private void parseResponse(String response)
    {
        try {
            RssHandler parser = new RssHandler();
            SAXParserFactory.newInstance()
                    .newSAXParser()
                    .parse(new InputSource(new StringReader(response)), parser);
            rssFeed = parser.getParsedItems();
            setImageLinks();
        } catch (Exception e) {
            Log.w("XMLParseException: ", e);
        }

    }

    public void setImageLinks()
    {
        for (NewsItem item : rssFeed) {
            item.setImageUrl(this.getImageLink(item.getDescription()));
        }
    }

    //parse description for any image or video links
    public String getImageLink(String description)
    {
        return (description.contains("<img ")) ?
                getImageString(description.substring(description.indexOf("<img ")))
                : "";
    }

    String getImageString(String img)
    {
        img = img.substring(img.indexOf("src=") + 5);
        int indexOf = img.indexOf("'");
        if (indexOf == -1) {
            indexOf = img.indexOf("\"");
        }
        return img.substring(0, indexOf);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        bus.register(this);
        networkModule.get("http://feeds.wired.com/wired/index");
        this.setListAdapter(null);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        bus.unregister(this);
    }

    @Subscribe
    public void busEvent(NewsEntity output)
    {
        Log.wtf("News Feed: ", output.getRaw());
        parseResponse(output.getRaw());
    }



    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks
    {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks()
    {
        @Override
        public void onItemSelected(String id)
        {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NewsItemsListFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//
//        // TODO: replace with a real list adapter.
//        setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(
//                getActivity(),
//                android.R.layout.simple_list_item_activated_1,
//                android.R.id.text1,
//                DummyContent.ITEMS));
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState)
//    {
//        super.onViewCreated(view, savedInstanceState);
//
//        // Restore the previously serialized activated item position.
//        if (savedInstanceState != null
//                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
//            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
//        }
//    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

//    @Override
//    public void onListItemClick(ListView listView, View view, int position, long id)
//    {
//        super.onListItemClick(listView, view, position, id);
//
//        // Notify the active callbacks interface (the activity, if the
//        // fragment is attached to one) that an item has been selected.
//        mCallbacks.onItemSelected(DummyContent.ITEMS.get(position).id);
//    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
//        if (mActivatedPosition != ListView.INVALID_POSITION) {
//            // Serialize and persist the activated item position.
//            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
//        }
    }

    //    /**
//     * Turns on activate-on-click mode. When this mode is on, list items will be
//     * given the 'activated' state when touched.
//     */
    public void setActivateOnItemClick(boolean activateOnItemClick)
    {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
//        getListView().setChoiceMode(activateOnItemClick
//                ? ListView.CHOICE_MODE_SINGLE
//                : ListView.CHOICE_MODE_NONE);
    }
//
//    private void setActivatedPosition(int position)
//    {
//        if (position == ListView.INVALID_POSITION) {
//            getListView().setItemChecked(mActivatedPosition, false);
//        } else {
//            getListView().setItemChecked(position, true);
//        }
//
//        mActivatedPosition = position;
//    }
}
