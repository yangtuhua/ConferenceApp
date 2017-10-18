package com.tuhua.conference.dagger.component;

import android.app.Activity;

import com.tuhua.conference.dagger.module.ActivityModule;
import com.tuhua.conference.dagger.scope.ActivityScope;

import dagger.Component;

/**
 * Created by yangtufa on 2017/7/7.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {

    Activity getActivity();

}
