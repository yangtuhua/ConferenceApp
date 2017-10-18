package com.tuhua.conference.dagger.module;

import android.app.Activity;

import com.tuhua.conference.dagger.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yangtufa on 2017/7/7.
 */
@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return activity;
    }
}
