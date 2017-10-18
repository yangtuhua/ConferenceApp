package com.tuhua.conference.base.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.facebook.stetho.common.LogUtil;
import com.tuhua.conference.R;
import com.tuhua.conference.base.app.EntApplication;
import com.tuhua.conference.base.mvp.BaseView;
import com.tuhua.conference.dagger.component.ActivityComponent;
import com.tuhua.conference.dagger.component.DaggerActivityComponent;
import com.tuhua.conference.dagger.module.ActivityModule;
import com.tuhua.conference.http.FeedResult;
import com.tuhua.conference.http.PagerResult;
import com.tuhua.conference.wiget.dialog.LoadingDialog;

import java.net.SocketTimeoutException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 基类Activity,所有的Activity都继承于此类
 * <p>
 * 此类集成了动态申请权限的方法
 * Created by yangtufa on 2017/7/19.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView, EasyPermissions.PermissionCallbacks {

    private static final int REQUEST_GET_PERMISSION = 333;
    private Unbinder unBinder;
    private String[] perms;
    private String requestPermissionText;
    private LoadingDialog loadingDialog;
    private AnimationDrawable loadingAnim;
    private ActivityTransition mTransition;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayout());

        initInject();

        unBinder = ButterKnife.bind(this);

        //保存上个页面的转场动画
        int ordinal = getIntent().getIntExtra(ActivityTransition.TRANSITION_ANIMATION, ActivityTransition.getDefaultInTransition().ordinal());
        this.mTransition = (ActivityTransition.values()[ordinal]);

        initEventAndSimpleData();
    }

    @Override
    protected void onDestroy() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        if (loadingAnim != null) {
            loadingAnim.stop();
            loadingAnim = null;
        }
        if (unBinder != null) {
            unBinder.unbind();
        }
        super.onDestroy();
    }

    /*** 提供获取activityComponent的方法*/
    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .applicationComponent(EntApplication.getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    /***页面提示*/
    @Override
    public void showMsg(String msg) {
        ToastUtils.showLong(msg);
    }

    /***center gravity吐司*/
    @Override
    public void showCenterLongToast(String msg) {
        //fixme 有问题,toast后，gravity设置回Bottom不生效
        ToastUtils.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        View toastView = ToastUtils.showCustomShort(R.layout.toast_center_long);
        TextView tvTips = (TextView) toastView.findViewById(R.id.tv_tips);
        tvTips.setText(msg);
    }

    /***错误提示*/
    @Override
    public void showError(String error) {
        ToastUtils.showLong(error);
    }

    /***页面异常处理*/
    @Override
    public void handleThrowable(Throwable throwable) {
        if (loadingDialog != null) loadingDialog.dismiss();
        if (loadingAnim != null) loadingAnim.stop();
        if (throwable instanceof SocketTimeoutException) {
            ToastUtils.showLong(R.string.com_time_out);
        } else if (throwable.getMessage().contains("Unsatisfiable Request (only-if-cached)")) {
            //504无网络的情况下抛出此异常
            ToastUtils.showLong(R.string.com_network_unavailable);
        } else {
            LogUtil.e("请求异常:" + throwable.getMessage());
            ToastUtils.showLong(R.string.com_request_fail);
        }
    }

    /***显示加载中对话框*/
    @Override
    public void showLoadingDialog() {
        showLoadingDialog(getString(R.string.com_loading_tips));
    }

    @Override
    public void showLoadingDialog(int stringId) {
        showLoadingDialog(getString(stringId));
    }

    @Override
    public void showLoadingDialog(String tipText) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        if (loadingAnim != null && loadingAnim.isRunning()) {
            loadingAnim.stop();
            loadingAnim = null;
        }
        loadingDialog = new LoadingDialog(this, R.layout.dialog_loading_window);
        ImageView ivLoading = (ImageView) loadingDialog.getView(R.id.iv_loading_image);
        TextView tvTip = (TextView) loadingDialog.getView(R.id.tv_tips);
        tvTip.setText(tipText);
        ivLoading.setBackgroundResource(R.drawable.anim_loading_dialog);
        loadingDialog.show();
        loadingAnim = (AnimationDrawable) ivLoading.getBackground();
        loadingAnim.start();
    }

    /***关闭加载中对话框*/
    @Override
    public void dismissLoadingDialog() {
        if (loadingAnim != null) loadingAnim.stop();
        if (loadingDialog != null) loadingDialog.dismiss();
    }

    /***设置分页数据*/
    @Override
    public void setPagerParams(PagerResult pagerResult) {
        //not implemented
    }

    @Override
    public void setPagerParams(FeedResult<PagerResult> feedResult) {
        //not implemented
    }

    @Override
    public void setToolBar(Toolbar toolBar, String title) {
        setToolBar(toolBar, title, true);
    }

    /***设置toolBar*/
    @Override
    public void setToolBar(Toolbar toolBar, String title, boolean needBackButton) {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(needBackButton);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        TextView tvTitle = (TextView) toolBar.findViewById(R.id.tv_title);
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
        if (needBackButton) {
            toolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }

    /***权限申请:在页面中申请需要的权限可以直接调用此方法并且重写相关的方法接收授权的结果即可*/
    public void getPhonePermission(String[] perms, String requestPermissionText) {
        this.perms = perms;
        this.requestPermissionText = requestPermissionText;
        getPermission();
    }

    private void getPermission() {
        if (EasyPermissions.hasPermissions(this, perms)) {
            hasPermissions();
        } else {
            EasyPermissions.requestPermissions(this, requestPermissionText, REQUEST_GET_PERMISSION, perms);
        }
    }

    /***授权成功回调*/
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
    }

    /***拒绝授权回调*/
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
    }

    /***已经有权限会回调此方法*/
    public void hasPermissions() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    protected View getEmptyView(String tips) {
        View emptyView = LayoutInflater.from(this).inflate(R.layout.view_empty_view, null);
        TextView tvTips = (TextView) emptyView.findViewById(R.id.tv_tips);
        tvTips.setText(tips);
        return emptyView;
    }

    protected View getEmptyView() {
        return getEmptyView("暂无数据");
    }

    protected abstract void initInject();

    protected abstract int getLayout();

    protected abstract void initEventAndSimpleData();
}

