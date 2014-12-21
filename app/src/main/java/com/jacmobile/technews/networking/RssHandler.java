package com.jacmobile.technews.networking;

import com.jacmobile.technews.networking.rss.entities.NewsItem;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by alex on 12/14/14.
 */
public class RssHandler extends DefaultHandler
{
    private StringBuffer buf;
    private ArrayList<NewsItem> feedItems;
    private NewsItem item;

    private boolean inItem = false;

    public ArrayList<NewsItem> getParsedItems()
    {
        return feedItems;
    }

    public NewsItem[] getArray()
    {
        NewsItem[] result = new NewsItem[feedItems.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = feedItems.get(i);
        return result;
    }

    //Called at the head of each new element
    @Override
    public void startElement(String uri, String name, String qName, Attributes attrs)
    {
        if ("channel".equals(name)) {
            feedItems = new ArrayList<NewsItem>();
        } else if ("item".equals(name)) {
            item = new NewsItem();
            inItem = true;
        } else if ("title".equals(name) && inItem) {
            buf = new StringBuffer();
        } else if ("link".equals(name) && inItem) {
            buf = new StringBuffer();
        } else if ("description".equals(name) && inItem) {
            buf = new StringBuffer();
        } else if ("pubDate".equals(name) && inItem) {
            buf = new StringBuffer();
        }
    }

    //Called at the tail of each element end
    @Override
    public void endElement(String uri, String name, String qName)
    {
        if ("item".equals(name)) {
            feedItems.add(item);
            inItem = false;
        } else if ("title".equals(name) && inItem) {
            item.title = buf.toString();
        } else if ("link".equals(name) && inItem) {
            item.link = buf.toString();
        } else if ("description".equals(name) && inItem) {
            item.description = buf.toString();
        } else if ("pubDate".equals(name) && inItem) {
            item.date = buf.toString();
        }

        buf = null;
    }

    //Called with character data inside elements
    @Override
    public void characters(char ch[], int start, int length)
    {
        if (buf != null) {
            for (int i = start; i < start + length; i++) {
                buf.append(ch[i]);
            }
        }
    }
}
