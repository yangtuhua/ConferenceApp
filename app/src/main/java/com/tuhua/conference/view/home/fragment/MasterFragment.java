package com.tuhua.conference.view.home.fragment;

import com.tuhua.conference.R;
import com.tuhua.conference.base.fragment.BaseRefreshFragment;
import com.tuhua.conference.view.home.contract.MasterContract;
import com.tuhua.conference.view.home.presenter.MasterPresenter;

import javax.inject.Inject;

/**
 * 大神Fragment
 * Created by yangtufa on 2017/10/22.
 */

public class MasterFragment extends BaseRefreshFragment<MasterPresenter> implements MasterContract.View{

    @Inject
    public MasterFragment(){}
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
        return R.layout.fragment_master;
    }
}
