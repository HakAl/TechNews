package com.jacmobile.technews.ui;

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
    public static final String TITLE = "ttl";
    public static final String URL = "url";
    @Inject Bus bus;
    @Inject NetworkModule networkModule;
    private ListView list;
    private NewsItem[] rssFeed = null;

    public static NewsListFragment newInstance(String title, String url, int position)
    {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        args.putString(TITLE, title);
        args.putString(URL, url);
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
            networkModule.get(getArguments().getString(URL));
            ((RootActivity) getActivity()).setWindowTitle(getArguments().getString(TITLE));
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

//        FeedParser parser = new FeedParser();
//        XmlPullParserFactory factory = null;
//        try {
//            factory = XmlPullParserFactory.newInstance();
//            factory.setNamespaceAware(true);
//            XmlPullParser xpp = factory.newPullParser();
//
//            xpp.setInput( new StringReader ( response ) );
//            parser.parseFeed(xpp);
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        } catch (FeedParser.UnknownFeedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        RssParser rss = new RssParser();
//
//        try{
//            RssFeed feed = rss.loadString(response);
//
//            // Gets the channel information of the feed and
//            // display its title
//            RssChannelBean channel = feed.getChannel();
//            System.out.println("Feed Title: " + channel.getTitle());
//
//            // Gets the image of the feed and display the image URL
//            RssImageBean image = feed.getImage();
//            System.out.println("Feed Image: " + image.getUrl());
//
//            // Gets and iterate the items of the feed
//            List<RssItemBean> items = feed.getItems();
//            for (int i=0; i<items.size(); i++){
//                RssItemBean item = items.get(i);
//                System.out.println("Title: " + item.getTitle());
//                System.out.println("Link : " + item.getLink());
//                System.out.println("Desc.: " + item.getDescription());
//            }
//
//        }catch(Exception e){
//            // Something to do if an exception occurs
//        }
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
//            item.setImageUrl(TextUtils.getImageUrl(item.getDescription()));
            Log.wtf("url", item.getImageUrl());
        }
    }

    final int LONG_OFFSET = 26;
    final int OFFSET = 5;

}