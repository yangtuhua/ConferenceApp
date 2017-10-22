package com.tuhua.conference.base.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.tuhua.conference.dagger.component.ApplicationComponent;
import com.tuhua.conference.dagger.component.DaggerApplicationComponent;
import com.tuhua.conference.dagger.module.ApplicationModule;
import com.tuhua.conference.service.InitializationService;

/**
 * Application
 * Created by yangtufa on 2017/7/19.
 */
public class ConApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //保存全局Application对象
        ContextHolder.setApplication(this);

        InitializationService.start(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    //static 代码段可以防止内存泄露,初始化下来刷新的样式
    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                //刷新时是否允许内容滑动
                layout.setEnableHeaderTranslationContent(false);
                return new MaterialHeader(context).setShowBezierWave(false);
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }

    /****向外提供获取 {@link ApplicationComponent}的方法*/
    public static ApplicationComponent getApplicationComponent() {
        return DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(ContextHolder.getApplication())).build();
    }

    @Override
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(callback);
    }
}
