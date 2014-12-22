package com.jacmobile.technews.networking.rss.channels;

/**
 * Created by acorll on 12/21/14.
 */
public class Channel
{
    private String title;
    private String link;

    public Channel()
    {
    }

    public String getTitle()
    {
        return title;
    }

    public Channel setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public String getLink()
    {
        return link;
    }

    public Channel setLink(String link)
    {
        this.link = link;
        return this;
    }
}