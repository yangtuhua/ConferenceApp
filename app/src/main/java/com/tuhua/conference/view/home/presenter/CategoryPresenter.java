package com.tuhua.conference.view.home.presenter;

import com.tuhua.conference.base.mvp.RxPrenster;
import com.tuhua.conference.http.RetrofitHelper;
import com.tuhua.conference.view.home.contract.CategoryContract;

import javax.inject.Inject;

/**
 * Created by yangtufa on 2017/10/19.
 */

public class CategoryPresenter extends RxPrenster<CategoryContract.View> implements CategoryContract.Presenter {
    private RetrofitHelper retrofitHelper;

    @Inject
    public CategoryPresenter(RetrofitHelper retrofitHelper) {
        this.retrofitHelper = retrofitHelper;
    }
}
