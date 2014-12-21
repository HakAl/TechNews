package com.jacmobile.technews.networking.rss.channels;

import com.jacmobile.technews.networking.rss.entities.Items;

import java.io.Serializable;

/**
 * Created by acorll on 12/21/14.
 */
public class ForbesTechChannel implements Serializable
{
    private Items items;
    private String title;
    private String link;
    private String description;
    private String lastBuildDate;
    private String docs;
    private String language;

    public ForbesTechChannel()
    {
    }

    public void setItems(Items items)
    {
        this.items = items;
    }

    public Items getItems()
    {
        return items;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getLastBuildDate()
    {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate)
    {
        this.lastBuildDate = lastBuildDate;
    }

    public String getDocs()
    {
        return docs;
    }

    public void setDocs(String docs)
    {
        this.docs = docs;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }
}