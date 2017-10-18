package com.tuhua.conference.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tuhua.conference.base.mvp.BasePresenter;

import javax.inject.Inject;

/**
 * 使用MVP模式的Activity需要继承此Activity
 * Created by yangtufa on 2017/8/12.
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity {

    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

        initEventAndData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    protected void initEventAndSimpleData() {
        //do nothing
    }

    protected abstract void initEventAndData();
}
