package com.tuhua.conference.view.home.fragment;

import com.tuhua.conference.R;
import com.tuhua.conference.base.fragment.BaseMvpFragment;
import com.tuhua.conference.view.home.contract.HomeContract;
import com.tuhua.conference.view.home.presenter.HomePresenter;

/**
 * Created by yangtufa on 2017/10/19.
 */

public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {


    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }
}