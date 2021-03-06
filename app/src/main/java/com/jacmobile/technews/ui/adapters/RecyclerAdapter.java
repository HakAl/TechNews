package com.jacmobile.technews.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jacmobile.technews.R;
import com.jacmobile.technews.networking.rss.channels.Channel;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by alex on 11/8/14.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
{
    private ArrayList<Channel> data;

    public static RecyclerAdapter newInstance(Channel[] data)
    {
        return new RecyclerAdapter(new ArrayList<>(Arrays.asList(data)));
    }

    public static RecyclerAdapter newInstance(ArrayList<Channel> data)
    {
        return new RecyclerAdapter(data);
    }

    private RecyclerAdapter(ArrayList<Channel> data)
    {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.title.setText(data.get(position).getTitle());
//        holder.description.setText(data.get(position).getDescription());

    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public final TextView title, description;

        public ViewHolder(View v)
        {
            super(v);
            title = (TextView) v.findViewById(R.id.card_title);
            description = (TextView) v.findViewById(R.id.card_description);
        }
    }
}