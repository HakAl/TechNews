package com.jacmobile.technews.injection;

import com.jacmobile.technews.app.AndroidAppModule;
import com.jacmobile.technews.app.DaggerApplication;

import dagger.Module;

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