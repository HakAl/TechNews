package com.jacmobile.technews.networking.rss.entities;

/**
 * Created by alex on 12/14/14.
 */
public class NewsEntity
{
    private String raw;

    public NewsEntity(String raw)
    {
        this.raw = raw;
    }

    public String getRaw()
    {
        return raw;
    }

    public void setRaw(String raw)
    {
        this.raw = raw;
    }
}
