package com.jacmobile.technews.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jacmobile.technews.R;
import com.jacmobile.technews.networking.NewsItem;
import com.jacmobile.technews.ui.ABaseActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by alex on 12/14/14.
 */
public class FeedAdapter extends ArrayAdapter<NewsItem>
{
    @Inject Picasso picasso;
    private int resId;
    private ArrayList<NewsItem> data;
    private Context context;

    public FeedAdapter(Context context, int resource, NewsItem[] objects)
    {
        super(context, resource, objects);
        ((ABaseActivity) context).inject(this);
        this.data = new ArrayList<>(Arrays.asList(objects));
        this.resId = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ListItem item;
        if (convertView == null) {
            item = new ListItem();
            convertView = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
            item.title = (TextView) convertView.findViewById(R.id.tv_test);
            item.image = (ImageView) convertView.findViewById(R.id.image_item);
            convertView.setTag(item);
        } else {
            item = (ListItem) convertView.getTag();
        }
        item.title.setText(data.get(position).getImageUrl());

        if (data.get(position).getImageUrl() == null) {
            Log.wtf("POSITION ", ""+position+"is null.");
        }else {
            Log.wtf("POSITION "+position, "-->"+data.get(position).getImageUrl()+"<---");
            picasso
                    .load(data.get(position).getImageUrl())
                    .placeholder(R.drawable.ic_launcher)
                    .into(item.image);
        }
        return convertView;
    }

    static class ListItem
    {
        TextView title;
        ImageView image;
    }
}
