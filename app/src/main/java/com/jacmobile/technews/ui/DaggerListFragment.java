package com.jacmobile.technews.ui;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jacmobile.technews.R;
import com.jacmobile.technews.networking.NetworkModule;
import com.jacmobile.technews.networking.NewsEntity;
import com.jacmobile.technews.networking.NewsItem;
import com.jacmobile.technews.networking.RssHandler;
import com.jacmobile.technews.ui.adapters.FeedAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by alex on 12/13/14.
 */
public class DaggerListFragment extends ListFragment
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

    /**
     * Created by alex on 12/15/14.
     */
    public static class TestListFragment extends DaggerFragment
    {
        @Inject Bus bus;
        @Inject NetworkModule networkModule;
        @Inject Picasso picasso;

        @Subscribe
        public void busEvent(NewsEntity output)
        {
            Log.wtf("News Feed: ", output.getRaw());
            parseResponse(output.getRaw());
        }

        private NewsItem[] rssFeed = null;


        private void parseResponse(String response)
        {
            //Process the response data
            try {
                SAXParserFactory factory =
                        SAXParserFactory.newInstance();
                SAXParser p = factory.newSAXParser();
                RssHandler parser = new RssHandler();
                p.parse(new InputSource(new StringReader(response)),
                        parser);

                rssFeed = parser.getArray();
                setImageLinks();

    //            feedList.setHasFixedSize(true);

    //            LinearLayoutManager staggeredLM = new LinearLayoutManager(getActivity());
    //            feedList.setLayoutManager(staggeredLM);

    //            RecyclerAdapter mAdapter = RecyclerAdapter.newInstance(rssFeed);
    //            feedList.setAdapter(mAdapter);

            } catch (Exception e) {
                Log.w("XMLParseException: ", e);
            }

        }

        public void setImageLinks()
        {
            for (NewsItem item : rssFeed) {
                item.setImageUrl(this.getDescription(item.getDescription()));
            }
//            for (NewsItem item : rssFeed) {
//                Log.wtf("LINK: ", item.getImageUrl());
//            }
            list.setAdapter(new FeedAdapter(getActivity(), R.layout.image_item, rssFeed));
        }


        public String getDescription(String description)
        {
            //parse description for any image or video links
            if (description.contains("<img ")) {
                String img = description.substring(description.indexOf("<img "));
                img = img.substring(img.indexOf("src=") + 5);
                int indexOf = img.indexOf("\"");

                img = img.substring(0, indexOf);

                return img;
            }
            return "";
        }

        @Override
        public void onStart()
        {
            super.onStart();
            bus.register(this);
            networkModule.get("http://feeds.wired.com/wired/index");
        }

        @Override
        public void onStop()
        {
            super.onStop();
            bus.unregister(this);
        }

        private ListView list;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            list = (ListView) inflater.inflate(R.layout.test_list, container, false);
            return list;
        }
    }
}