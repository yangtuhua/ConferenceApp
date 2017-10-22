package com.tuhua.conference.view.home;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.FragmentUtils;
import com.tuhua.conference.R;
import com.tuhua.conference.base.activity.BaseActivity;
import com.tuhua.conference.view.home.fragment.HomeFragment;
import com.tuhua.conference.view.home.fragment.MineFragment;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 首页
 */
public class MainActivity extends BaseActivity {

    @Inject
    HomeFragment homeFragment;

    @Inject
    MineFragment mineFragment;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.radio_1)
    RadioButton radio1;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    private Fragment currentFragment;
    private Fragment selectedFragment;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndSimpleData() {
        selectedFragment = homeFragment;
        currentFragment = selectedFragment;
        FragmentUtils.addFragment(getSupportFragmentManager(), homeFragment, R.id.content);

        setListener();
    }

    /***监听器*/
    private void setListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radio_1:
                        selectedFragment = homeFragment;
                        break;
                    case R.id.radio_2:
                        selectedFragment = mineFragment;
                        break;
                }
                changeFragmentPage();
            }
        });
    }

    private void changeFragmentPage() {
        if (selectedFragment != currentFragment) {
            FragmentUtils.hideFragment(currentFragment);
            if (!selectedFragment.isAdded()) {
                FragmentUtils.addFragment(getSupportFragmentManager(), selectedFragment, R.id.content);
            } else {
                FragmentUtils.showFragment(selectedFragment);
            }
            currentFragment = selectedFragment;
        }
    }
}
