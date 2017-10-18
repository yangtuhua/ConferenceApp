package com.tuhua.conference.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tuhua.conference.base.mvp.BasePresenter;
import com.tuhua.conference.http.FeedResult;
import com.tuhua.conference.http.PagerResult;

/**
 * 列表(含刷新和加载更多)继承此类
 * Created by yangtufa on 2017/8/12.
 */

public abstract class BaseRefreshActivity<T extends BasePresenter> extends BaseMvpActivity<T> {

    public static final String CURRENT_PAGE = "currentpage";
    public static final String PAGE_SIZE = "pageSize";
    private double currentPage = 1;
    private double pageSize = 20;
    private double totalRow = 0;

    private SmartRefreshLayout smartRefreshLayout;

    protected boolean isRefresh = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /***初始化刷新控件*/
    protected void initSmartRefreshLayout(final SmartRefreshLayout smartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout;
        smartRefreshLayout.autoRefresh();
        smartRefreshLayout.setReboundDuration(200);

        //刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                isRefresh = true;
                currentPage = 1;
                pullToRefresh();
            }
        });

        //加载更多
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshLayout) {
                if (totalRow <= pageSize * currentPage) {
                    //TODO 没有更多内容了
                    smartRefreshLayout.finishLoadmore();
                    ToastUtils.showShort("没有更多了");
                } else {
                    isRefresh = false;
                    currentPage++;
                    pullToLoadMore();
                }
            }
        });
    }

    /***加载完数据后,关闭加载和刷新*/
    protected void loadComplete() {
        if (smartRefreshLayout != null) {
            smartRefreshLayout.finishLoadmore(0);
            smartRefreshLayout.finishRefresh(0);
        }
    }

    @Override
    public void setPagerParams(FeedResult<PagerResult> feedResult) {
        if (feedResult != null && feedResult.isStatus()) {
            setPagerParams(feedResult.getResult());
        }
    }

    /***设置分页信息*/
    public void setPagerParams(PagerResult pagerResult) {
        if (pagerResult != null) {
            setTotalRow(pagerResult.getTotalRow());
            setCurrentPage(pagerResult.getCurrentpage());
            setPageSize(pagerResult.getPageSize());
        }
    }

    @Override
    public void handleThrowable(Throwable throwable) {
        //关闭刷新
        smartRefreshLayout.finishLoadmore();
        smartRefreshLayout.finishRefresh();

        super.handleThrowable(throwable);
    }

    public int getCurrentPage() {
        return (int) currentPage;
    }

    public void setCurrentPage(double currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return (int) pageSize;
    }

    public void setPageSize(double pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRow() {
        return (int) totalRow;
    }

    public void setTotalRow(double totalRow) {
        this.totalRow = totalRow;
    }

    /***刷新回调*/
    protected abstract void pullToRefresh();

    /***加载更多回调*/
    protected abstract void pullToLoadMore();
}
