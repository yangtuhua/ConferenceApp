package com.tuhua.conference.dagger.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.tuhua.conference.dagger.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yangtufa on 2017/7/7.
 */
@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    Activity providesActivity() {
        return fragment.getActivity();
    }
}
