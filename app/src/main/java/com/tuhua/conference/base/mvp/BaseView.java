package com.tuhua.conference.base.mvp;

import android.support.v7.widget.Toolbar;

import com.tuhua.conference.http.FeedResult;
import com.tuhua.conference.http.PagerResult;


/**
 * Created by yangtufa on 2017/8/12.
 */

public interface BaseView {

    void showMsg(String msg);

    void showCenterLongToast(String msg);//Gravity =center的吐司

    void showError(String error);

    void handleThrowable(Throwable throwable);

    void setPagerParams(PagerResult pagerResult);

    void setPagerParams(FeedResult<PagerResult> feedResult);

    void showLoadingDialog();

    void showLoadingDialog(int stringId);

    void showLoadingDialog(String tipText);

    void dismissLoadingDialog();

    void setToolBar(Toolbar toolBar, String title);

    void setToolBar(Toolbar toolBar, String title, boolean needBackButton);
}
