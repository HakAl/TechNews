package com.jacmobile.technews.app;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;

import com.jacmobile.technews.injection.ForApplication;
import com.jacmobile.technews.ui.NewsListFragment;
import com.jacmobile.technews.ui.WebViewFragment;
import com.jacmobile.technews.ui.adapters.FeedAdapter;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module (
        complete = false,
        library = true,
        injects = {
                FeedAdapter.class,
                NewsListFragment.class,
                WebViewFragment.class
//                RootActivity.class
        }
)
public class AndroidAppModule
{
    static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB
    /* package */ static Context sApplicationContext = null;

    /**
     * Allow the application context to be injected but require that it be annotated with
     * to explicitly differentiate it from an activity context.
     */
    @Provides @Singleton @ForApplication Context provideApplicationContext()
    {
        return sApplicationContext;
    }

    @Provides @Singleton LayoutInflater provideLayoutInflater()
    {
        return LayoutInflater.from(sApplicationContext);
    }

    @Provides @Singleton Handler provideHandler()
    {
        return new Handler(Looper.getMainLooper());
    }

    @Provides @Singleton ExecutorService provideExecutorService()
    {
        return Executors.newCachedThreadPool();
    }

    @Provides @Singleton Bus provideBus()
    {
        return new Bus(ThreadEnforcer.ANY);
    }

    @Provides @Singleton OkHttpClient provideOkHttpClient() {
        return createOkHttpClient();
    }

    static OkHttpClient createOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        // Install an HTTP cache in the application cache directory.
        try {
            File cacheDir = new File(sApplicationContext.getCacheDir(), "http");
            Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
            client.setCache(cache);
        } catch (IOException e) {
            Log.e("Unable to install disk cache.", e.toString());
        }
        return client;
    }
}