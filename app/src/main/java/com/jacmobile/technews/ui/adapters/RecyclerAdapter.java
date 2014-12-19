package com.jacmobile.technews.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jacmobile.technews.R;
import com.jacmobile.technews.networking.NewsItem;

import java.util.ArrayList;

/**
 * Created by alex on 11/8/14.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
{
    private ArrayList<NewsItem> data;

    public static RecyclerAdapter newInstance(ArrayList<NewsItem> data)
    {
        return new RecyclerAdapter(data);
    }

    private RecyclerAdapter(ArrayList<NewsItem> data)
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
        holder.description.setText(data.get(position).getDescription());

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