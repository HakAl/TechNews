package com.jacmobile.technews.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jacmobile.technews.R;
import com.jacmobile.technews.networking.RssUrls;
import com.jacmobile.technews.ui.adapters.RecyclerAdapter;


/**
 * Created by alex on 12/15/14.
 */
public class FeedListFragment extends DaggerFragment
{
    @SuppressWarnings("unused")
    public static final String POSITION = "pos";
    private FeedListObserver observer;
    private RecyclerView recyclerView;

    public static FeedListFragment newInstance(int position)
    {
        FeedListFragment fragment = new FeedListFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try {
            this.observer = (FeedListObserver) activity;
        } catch (ClassCastException e) {
            Log.wtf("Activity must implement", "FeedListObserver.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler, container, false);
        this.recyclerView.setHasFixedSize(true);
//        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(RecyclerAdapter.newInstance(RssUrls.getChannels()));
        this.recyclerView.addOnItemTouchListener(getListener());
        this.restoreListState(getArguments());
        return recyclerView;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        this.saveListState();
    }

    void saveListState()
    {
        getArguments().putInt(POSITION, ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition());
    }

    void restoreListState(Bundle args)
    {
        if (args != null) recyclerView.getLayoutManager().scrollToPosition(args.getInt(POSITION));
    }

    RecyclerItemClickListener getListener()
    {
        return new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                observer.onItemClick(position);
            }
        });
    }
}