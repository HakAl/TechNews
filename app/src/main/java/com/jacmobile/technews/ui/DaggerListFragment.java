package com.jacmobile.technews.ui;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.jacmobile.technews.R;
import com.jacmobile.technews.networking.NetworkModule;
import com.jacmobile.technews.networking.rss.entities.NewsEntity;
import com.jacmobile.technews.networking.rss.entities.NewsItem;
import com.jacmobile.technews.networking.RssHandler;
import com.jacmobile.technews.ui.adapters.FeedAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.inject.Inject;
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

}