package com.jacmobile.technews.networking;

public class NewsItem
{
    public String title;
    public String link;
    public String imageUrl;
    public String date;
    public String description;
    public String mediaContent;

    public String getMediaContent()
    {
        return mediaContent;
    }

    public void setMediaContent(String mediaContent)
    {
        this.mediaContent = mediaContent;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString()
    {
        return title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }
}
