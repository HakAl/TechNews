package com.jacmobile.technews.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jacmobile.technews.R;
import com.jacmobile.technews.networking.NewsItem;
import com.jacmobile.technews.ui.ABaseActivity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by alex on 12/14/14.
 */
public class FeedAdapter extends ArrayAdapter<NewsItem>
{
    private ArrayList<NewsItem> data;

    public FeedAdapter(Context context, int resource, NewsItem[] objects)
    {
        super(context, resource, objects);
        ((ABaseActivity) context).inject(this);
        this.data = new ArrayList<>(Arrays.asList(objects));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ListItem item;
        if (convertView == null) {
            item = new ListItem();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
            item.title = (TextView) convertView.findViewById(R.id.news_item_title);
            item.image = (ImageView) convertView.findViewById(R.id.news_item_image);
            convertView.setTag(item);
        } else {
            item = (ListItem) convertView.getTag();
        }

        NewsItem currentItem = data.get(position);

        item.title.setText(currentItem.getTitle());

        Log.wtf("Item "+position,currentItem.getDescription());
        Glide.with(getContext())
                .load(currentItem.getImageUrl())
                .centerCrop()
//                .placeholder(R.drawable.ic_launcher)
                .crossFade()
                .into(item.image);

        return convertView;
    }

    static class ListItem
    {
        TextView title;
        ImageView image;
    }
}