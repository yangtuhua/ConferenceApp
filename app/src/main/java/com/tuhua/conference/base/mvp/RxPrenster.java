package com.tuhua.conference.base.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 基于RxJava2的Presenter封装,用于控制订阅的生命周期
 * Created by yangtufa on 2017/8/12.
 */
public class RxPrenster<T extends BaseView> implements BasePresenter<T> {

    protected T mView;

    private CompositeDisposable compositeDisposable;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        unSubscribe();
    }

    public void unSubscribe() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }

    public void addSubscribe(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }
}
