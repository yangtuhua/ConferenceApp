package com.tuhua.conference.view.home.fragment;

import com.tuhua.conference.R;
import com.tuhua.conference.base.fragment.BaseMvpFragment;
import com.tuhua.conference.view.home.contract.MineContract;
import com.tuhua.conference.view.home.presenter.MinePresenter;

import javax.inject.Inject;

/**
 * 我的
 * Created by yangtufa on 2017/10/19.
 */

public class MineFragment extends BaseMvpFragment<MinePresenter> implements MineContract.View {

    @Inject
    public MineFragment() {
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_mine;
    }
}
