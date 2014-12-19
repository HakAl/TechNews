package com.jacmobile.technews.injection;

import android.content.Context;

import com.jacmobile.technews.app.AndroidAppModule;
import com.jacmobile.technews.app.DaggerApplication;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                DaggerApplication.class
        },
        complete = true,    // Here it enables object graph validation
        library = true,
        addsTo = AndroidAppModule.class // Important for object graph validation at compile time
)
public class ApplicationScopeModule
{
}