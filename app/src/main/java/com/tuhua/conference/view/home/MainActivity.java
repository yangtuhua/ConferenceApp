package com.tuhua.conference.view.home;

import com.blankj.utilcode.util.LogUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.tuhua.conference.R;
import com.tuhua.conference.base.activity.BaseActivity;

import butterknife.OnClick;

/**
 * 首页
 */
public class MainActivity extends BaseActivity {

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

    }

    @OnClick(R.id.bt_register)
    public void register() {
        try {
            EMClient.getInstance().createAccount("13660260845", "123yangtuhua");//同步方法
        } catch (HyphenateException e) {
            e.printStackTrace();
            LogUtils.e("注册异常" + e.getMessage());
        }
    }
}
