package com.tuhua.conference.base.mvp;

/**
 * Created by yangtufa on 2017/8/12.
 */

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void detachView();
}
