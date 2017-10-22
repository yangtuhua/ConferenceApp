package com.tuhua.conference.view.home.fragment;

import com.tuhua.conference.R;
import com.tuhua.conference.base.fragment.BaseRefreshFragment;
import com.tuhua.conference.view.home.contract.CategoryContract;
import com.tuhua.conference.view.home.presenter.CategoryPresenter;

import javax.inject.Inject;

/**
 * 分类Fragment
 * Created by yangtufa on 2017/10/22.
 */

public class CategoryFragment extends BaseRefreshFragment<CategoryPresenter> implements CategoryContract.View{

    @Inject
    public CategoryFragment(){}

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
        return R.layout.fragment_category;
    }
}
