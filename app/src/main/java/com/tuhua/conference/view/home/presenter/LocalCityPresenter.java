package com.tuhua.conference.view.home.presenter;

import com.tuhua.conference.base.mvp.RxPrenster;
import com.tuhua.conference.http.RetrofitHelper;
import com.tuhua.conference.view.home.contract.LocalCityContract;

import javax.inject.Inject;

/**
 * Created by yangtufa on 2017/10/19.
 */

public class LocalCityPresenter extends RxPrenster<LocalCityContract.View> implements LocalCityContract.Presenter {
    private RetrofitHelper retrofitHelper;

    @Inject
    public LocalCityPresenter(RetrofitHelper retrofitHelper) {
        this.retrofitHelper = retrofitHelper;
    }
}
