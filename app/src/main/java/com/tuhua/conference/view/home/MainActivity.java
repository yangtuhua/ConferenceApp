package com.tuhua.conference.view.home;

import com.tuhua.conference.R;
import com.tuhua.conference.base.activity.BaseActivity;

/**
 * 首页
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndSimpleData() {

    }
}
