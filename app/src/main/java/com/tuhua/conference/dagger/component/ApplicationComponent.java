package com.tuhua.conference.dagger.component;


import com.tuhua.conference.base.app.ConApplication;
import com.tuhua.conference.dagger.module.ApplicationModule;
import com.tuhua.conference.dagger.module.HttpModule;
import com.tuhua.conference.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by yangtufa on 2017/7/7.
 */
@Singleton
@Component(modules = {ApplicationModule.class, HttpModule.class})
public interface ApplicationComponent {
    ConApplication getApplication();

    RetrofitHelper getRetrofitHelper();

    OkHttpClient getOkhttpClient();
}
