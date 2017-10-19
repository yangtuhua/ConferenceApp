package com.tuhua.conference.view.home.presenter;

import com.tuhua.conference.base.mvp.RxPrenster;
import com.tuhua.conference.http.RetrofitHelper;
import com.tuhua.conference.view.home.contract.HomeContract;

import javax.inject.Inject;

/**
 * Created by yangtufa on 2017/10/19.
 */

public class HomePresenter extends RxPrenster<HomeContract.View> implements HomeContract.Presenter {
    private RetrofitHelper retrofitHelper;

    @Inject
    public HomePresenter(RetrofitHelper retrofitHelper) {
        this.retrofitHelper = retrofitHelper;
    }
}
