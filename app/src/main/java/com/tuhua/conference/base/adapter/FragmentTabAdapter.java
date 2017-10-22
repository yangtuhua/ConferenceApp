package com.tuhua.conference.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 我的收藏ViewPager adapter
 * <p>
 * Created by yangtufa on 2017/4/7.
 */

public class FragmentTabAdapter extends FragmentPagerAdapter {

    private final String[] titleArray;
    private List<Fragment> fragmentList;

    public FragmentTabAdapter(FragmentManager fm, List<Fragment> fragments, String[] titleArray) {
        super(fm);
        this.fragmentList = fragments;

        this.titleArray = titleArray;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleArray[position];
    }
}
