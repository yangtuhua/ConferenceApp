package com.tuhua.conference.view.home.presenter;

import com.tuhua.conference.base.mvp.RxPrenster;
import com.tuhua.conference.http.RetrofitHelper;
import com.tuhua.conference.view.home.contract.MineContract;

import javax.inject.Inject;

/**
 * Created by yangtufa on 2017/10/19.
 */

public class MinePresenter extends RxPrenster<MineContract.View> implements MineContract.Presenter {
    private RetrofitHelper retrofitHelper;

    @Inject
    public MinePresenter(RetrofitHelper retrofitHelper) {
        this.retrofitHelper = retrofitHelper;
    }
}
