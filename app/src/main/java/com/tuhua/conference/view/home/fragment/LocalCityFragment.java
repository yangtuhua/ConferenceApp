package com.tuhua.conference.view.home.fragment;

import com.tuhua.conference.R;
import com.tuhua.conference.base.fragment.BaseRefreshFragment;
import com.tuhua.conference.view.home.contract.LocalCityContract;
import com.tuhua.conference.view.home.presenter.LocalCityPresenter;

import javax.inject.Inject;

/**
 * Created by yangtufa on 2017/10/22.
 */

public class LocalCityFragment extends BaseRefreshFragment<LocalCityPresenter> implements LocalCityContract.View {

    @Inject
    public LocalCityFragment() {
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void pullToRefresh() {

    }

    @Override
    protected void pullToLoadMore() {

    }

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_local_city;
    }
}
