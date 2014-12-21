package com.jacmobile.technews.app;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.jacmobile.technews.injection.ApplicationScopeModule;
import com.jacmobile.technews.injection.DaggerInjector;
import com.squareup.okhttp.OkHttpClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;

/**
 * Base class of a dagger enabled application
 */
public class DaggerApplication extends Application implements DaggerInjector
{
    @Inject OkHttpClient okHttpClient;
    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate()
    {
        super.onCreate();

        AndroidAppModule sharedAppModule = new AndroidAppModule();

        // bootstrap. So that it allows no-arg constructor in AndroidAppModule
        sharedAppModule.sApplicationContext = this.getApplicationContext();

        List<Object> modules = new ArrayList<Object>();
        modules.add(sharedAppModule);
        modules.addAll(getAppModules());

        mObjectGraph = ObjectGraph.create(modules.toArray());

        mObjectGraph.inject(this);

        initGlide();
    }

    private void initGlide()
    {
        Glide.get(this)
             .register(
                    GlideUrl.class,
                    InputStream.class,
                    new OkHttpUrlLoader.Factory(okHttpClient));
    }

    protected List<Object> getAppModules()
    {
        return Collections.<Object>singletonList(new ApplicationScopeModule());
    }

    @Override
    public void inject(Object object)
    {
        mObjectGraph.inject(object);
    }

    @Override
    public ObjectGraph getObjectGraph()
    {
        return mObjectGraph;
    }
}