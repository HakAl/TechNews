package com.jacmobile.technews.injection;

import android.app.Activity;
import android.content.Context;

import com.jacmobile.technews.ui.ABaseActivity;
import com.jacmobile.technews.ui.ContentView;
import com.jacmobile.technews.ui.RootActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        complete = true,    // Here we enable object graph validation
        library = true,
        addsTo = ApplicationScopeModule.class, // Important for object graph validation at compile time
        injects = {
                RootActivity.class
        }
)
public class ActivityScopeModule {

    private final ABaseActivity mActivity;

    public ActivityScopeModule(ABaseActivity activity) {
        mActivity = activity;
    }

    @Provides
    @Singleton
    @ForActivity
    Context providesActivityContext() {
        return mActivity;
    }

    @Provides
    @Singleton
    Activity providesActivity() {
        return mActivity;
    }


    @Provides @Singleton ContentView provideContentView() {
        return ContentView.DEFAULT;
    }
}