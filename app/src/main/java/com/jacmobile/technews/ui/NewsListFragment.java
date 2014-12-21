package com.jacmobile.technews.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.jacmobile.technews.R;
import com.jacmobile.technews.networking.NetworkModule;
import com.jacmobile.technews.networking.RssHandler;
import com.jacmobile.technews.networking.rss.entities.NewsEntity;
import com.jacmobile.technews.networking.rss.entities.NewsItem;
import com.jacmobile.technews.ui.adapters.FeedAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.inject.Inject;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by alex on 12/15/14.
 */
public class NewsListFragment extends DaggerFragment
{
    @SuppressWarnings("unused")
    public static final String POSITION = "pos";
    @Inject Bus bus;
    @Inject NetworkModule networkModule;
    private ListView list;
    private NewsItem[] rssFeed = null;

    public static NewsListFragment newInstance()
    {
        return newInstance(0);
    }

    public static NewsListFragment newInstance(int position)
    {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe
    public void busEvent(NewsEntity output)
    {
        parseResponse(output.getRaw());
    }

    @Override
    public void onStart()
    {
        super.onStart();
        bus.register(this);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        this.saveListState();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        bus.unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        RelativeLayout parent = (RelativeLayout) inflater.inflate(R.layout.news_list, container, false);
        this.list = (ListView) parent.findViewById(R.id.news_list);

        if (rssFeed == null) {
            networkModule.get("http://www.wired.co.uk/rss");
//            networkModule.get("http://feeds.wired.com/wired/index");
//            networkModule.get("http://www.forbes.com/technology/feed/");
        } else {
            list.setAdapter(new FeedAdapter(getActivity(), R.layout.image_item, rssFeed));
            this.restoreListState(getArguments());
        }
        return parent;
    }

    void saveListState()
    {
        getArguments().putInt(POSITION, list.getFirstVisiblePosition());
    }

    void restoreListState(Bundle args)
    {
        if (args != null) list.setSelection(args.getInt(POSITION));
    }

    private void parseResponse(String response)
    {
        try {
            RssHandler parser = new RssHandler();
            SAXParserFactory.newInstance()
                    .newSAXParser().parse(
                    new InputSource(new StringReader(response)), parser);
            rssFeed = parser.getArray();
            setImageLinks();
            list.setAdapter(new FeedAdapter(getActivity(), R.layout.image_item, rssFeed));
            restoreListState(getArguments());
        } catch (Exception e) {
            Log.w("XMLParseException: ", e);
        }
    }

    public void setImageLinks()
    {
        for (NewsItem item : rssFeed) {
            item.setImageUrl(this.getImageUrl(item.getDescription()));
        }
    }

    //parse description for any image or video links
    public String getImageUrl(String description)
    {
        if (description.contains("<img ")) {
            String img = description.substring(description.indexOf("<img "));
            img = img.substring(img.indexOf("src=") + 5);
            int indexOf = img.indexOf("\"");
            img = img.substring(0, indexOf);
            return img;
        }
        return "";
    }
}