package com.jacmobile.technews.networking;

/**
 * Created by User on 7/19/2014.
 */
public interface NetworkService
{
    public void get(String url);
    public void post(String url, String data);
}
