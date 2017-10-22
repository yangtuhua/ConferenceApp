package com.tuhua.conference.view.home.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.blankj.utilcode.util.ConvertUtils;
import com.tuhua.conference.R;
import com.tuhua.conference.base.adapter.FragmentTabAdapter;
import com.tuhua.conference.base.fragment.BaseMvpFragment;
import com.tuhua.conference.view.home.contract.HomeContract;
import com.tuhua.conference.view.home.presenter.HomePresenter;
import com.tuhua.conference.wiget.tabIndicator.ColorFlipPagerTitleView;
import com.tuhua.conference.wiget.tabIndicator.CommonNavigator;
import com.tuhua.conference.wiget.tabIndicator.CommonNavigatorAdapter;
import com.tuhua.conference.wiget.tabIndicator.IPagerIndicator;
import com.tuhua.conference.wiget.tabIndicator.IPagerTitleView;
import com.tuhua.conference.wiget.tabIndicator.LinePagerIndicator;
import com.tuhua.conference.wiget.tabIndicator.MagicIndicator;
import com.tuhua.conference.wiget.tabIndicator.SimplePagerTitleView;
import com.tuhua.conference.wiget.tabIndicator.ViewPagerHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by yangtufa on 2017/10/19.
 */

public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {

    @Inject
    CategoryFragment categoryFragment;

    @Inject
    MasterFragment masterFragment;

    @Inject
    LocalCityFragment localCityFragment;
    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Inject
    public HomeFragment() {
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolbar, "");

        initTabIndicator();
    }

    /***初始化页面指示器*/
    private void initTabIndicator() {
        final List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(categoryFragment);
        fragmentList.add(masterFragment);
        fragmentList.add(localCityFragment);

        //标题
        final String[] titleArray = getResources().getStringArray(R.array.homePageArray);
        viewPager.setAdapter(new FragmentTabAdapter(getChildFragmentManager(), fragmentList, titleArray));
        viewPager.setOffscreenPageLimit(fragmentList.size());

        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setScrollPivotX(0.65f);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView pagerTitleView = new ColorFlipPagerTitleView(context);
                pagerTitleView.setText(titleArray[index]);
                pagerTitleView.setTextSize(16);
                pagerTitleView.setNormalColor(getResources().getColor(R.color.color_999));
                pagerTitleView.setSelectedColor(getResources().getColor(R.color.color_111));
                pagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return pagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(ConvertUtils.dp2px(4));
                indicator.setLineWidth(ConvertUtils.dp2px(10));
                indicator.setRoundRadius(ConvertUtils.dp2px(2));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(getResources().getColor(R.color.colorAccent));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

}
