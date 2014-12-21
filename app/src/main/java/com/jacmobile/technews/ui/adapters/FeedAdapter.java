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
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jacmobile.technews.R;
import com.jacmobile.technews.networking.NewsItem;
import com.jacmobile.technews.ui.ABaseActivity;
import com.jacmobile.technews.ui.RootActivity;
import com.jacmobile.technews.ui.WebViewFragment;
import com.jacmobile.technews.utils.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by alex on 12/14/14.
 */
public class FeedAdapter extends ArrayAdapter<NewsItem>
{
    private ArrayList<NewsItem> data;
    private RootActivity context;

    public FeedAdapter(Context context, int resource, NewsItem[] objects)
    {
        super(context, resource, objects);
        this.context = (RootActivity) context;
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
            item.date = (TextView) convertView.findViewById(R.id.news_item_date);
            convertView.setTag(item);
        } else {
            item = (ListItem) convertView.getTag();
        }
        NewsItem currentItem = getItem(position);
        Log.wtf("media:content", currentItem.getMediaContent());
        item.title.setText(currentItem.getTitle());
        item.date.setText(TextUtils.cleanNewsDate(currentItem.getDate()));
        Glide.with(getContext())
                .load(currentItem.getImageUrl())
                .centerCrop()
                .crossFade()
                .into(item.image);
        this.setClickListener(convertView, position);
        return convertView;
    }

    @Override
    public NewsItem getItem(int position)
    {
        return data.get(position);
    }

    private void setClickListener(View convertView, final int position)
    {
        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                context.newFragment(RootActivity.WEBVIEW_FRAGMENT, "Wired UK", getItem(position).getLink());
//                context.getFragmentManager().beginTransaction()
//                        .replace(R.id.container,
//                                WebViewFragment.newInstance(
//                                        "Wired UK", getItem(position).getLink())).commit();
            }
        });
    }

    static class ListItem
    {
        TextView title;
        TextView date;
        ImageView image;
    }
}