package com.tuhua.conference.dagger.component;

import android.app.Activity;

import com.tuhua.conference.dagger.module.FragmentModule;
import com.tuhua.conference.dagger.scope.FragmentScope;

import dagger.Component;

/**
 * Created by yangtufa on 2017/7/7.
 */
@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = {FragmentModule.class})
public interface FragmentComponent {
    Activity getActivity();

}
