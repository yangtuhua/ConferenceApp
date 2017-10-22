package com.tuhua.conference.view.home.presenter;

import com.tuhua.conference.base.mvp.RxPrenster;
import com.tuhua.conference.http.RetrofitHelper;
import com.tuhua.conference.view.home.contract.MasterContract;

import javax.inject.Inject;

/**
 * Created by yangtufa on 2017/10/19.
 */

public class MasterPresenter extends RxPrenster<MasterContract.View> implements MasterContract.Presenter {
    private RetrofitHelper retrofitHelper;

    @Inject
    public MasterPresenter(RetrofitHelper retrofitHelper) {
        this.retrofitHelper = retrofitHelper;
    }
}
