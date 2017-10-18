package com.tuhua.conference.dagger.module;


import com.tuhua.conference.base.app.EntApplication;
import com.tuhua.conference.http.EntApi;
import com.tuhua.conference.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yangtufa on 2017/7/7.
 */
@Module
public class ApplicationModule {

    private final EntApplication bookApplication;

    public ApplicationModule(EntApplication bookApplication) {
        this.bookApplication = bookApplication;
    }

    @Singleton
    @Provides
    EntApplication providesApplication() {
        return bookApplication;
    }

    @Singleton
    @Provides
    RetrofitHelper providesRetrofitHelper(EntApi entApi) {
        return new RetrofitHelper(entApi);
    }
}
