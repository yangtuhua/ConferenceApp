package com.tuhua.conference.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.tuhua.conference.base.mvp.BasePresenter;

import javax.inject.Inject;

/**
 * 采用MVP模式Fragment需要继承此Fragment
 * Created by yangtufa on 2017/8/12.
 */

public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseFragment {

    @Inject
    protected T mPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

        initEventAndData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    protected void initEventAnSimpledData() {
        //do nothing
    }

    protected abstract void initEventAndData();
}
