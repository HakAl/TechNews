package com.jacmobile.technews.networking;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.otto.Bus;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by alex on 12/13/14.
 */
@Singleton
public class NetworkModule implements NetworkService
{
    @Inject Bus bus;
    @Inject OkHttpClient okHttpClient;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override public void get(String url)
    {
        new GetTask().execute(url);
    }

    @Override public void post(String url, String json)
    {
        new PostTask().execute(url, json);
    }

    void postExecute(String result)
    {
        bus.register(this);
        bus.post(new NewsEntity(result));
        bus.unregister(this);
    }

    private class GetTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... url)
        {
            Request request = new Request.Builder()
                    .url(url[0])
                    .build();
            try {
                return okHttpClient.newCall(request).execute().body().string();
            } catch (IOException e) {
                Log.wtf("Exception - FeedModule: ", e.toString());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result)
        {
            postExecute(result);
        }
    }

    private class PostTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... data)
        {
            RequestBody body = RequestBody.create(JSON, data[1]);
            Request request = new Request.Builder()
                    .url(data[0])
                    .post(body)
                    .build();
            try {
                return okHttpClient.newCall(request).execute().body().string();
            } catch (IOException e) {
                Log.wtf("Exception - FeedModule: ", e.toString());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result)
        {
            postExecute(result);
        }
    }
}