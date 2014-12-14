package com.jacmobile.technews.app;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;

import com.jacmobile.technews.injection.ForApplication;
import com.jacmobile.technews.ui.NewsItemsDetailActivity;
import com.jacmobile.technews.ui.NewsItemsListActivity;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module (
        complete = false,
        library = true,
        injects = {
                NewsItemsListActivity.class,
                NewsItemsDetailActivity.class
        }
)
public class AndroidAppModule
{
    /* package */ static Context sApplicationContext = null;

    /**
     * Allow the application context to be injected but require that it be annotated with
     * to explicitly differentiate it from an activity context.
     */
    @Provides @Singleton @ForApplication Context provideApplicationContext()
    {
        return sApplicationContext;
    }

    @Provides @Singleton LocationManager provideLocationManager()
    {
        return (LocationManager) sApplicationContext.getSystemService(Context.LOCATION_SERVICE);
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

    @Provides @Singleton OkHttpClient provideOkHttpClient()
    {
        return new OkHttpClient();
    }

    @Provides @Singleton Bus provideBus()
    {
        return new Bus(ThreadEnforcer.ANY);
    }
}