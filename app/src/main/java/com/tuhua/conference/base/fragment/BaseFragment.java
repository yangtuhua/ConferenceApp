package com.tuhua.conference.base.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.facebook.stetho.common.LogUtil;
import com.tuhua.conference.R;
import com.tuhua.conference.base.app.ConApplication;
import com.tuhua.conference.base.mvp.BaseView;
import com.tuhua.conference.dagger.component.DaggerFragmentComponent;
import com.tuhua.conference.dagger.component.FragmentComponent;
import com.tuhua.conference.dagger.module.FragmentModule;
import com.tuhua.conference.http.FeedResult;
import com.tuhua.conference.http.PagerResult;
import com.tuhua.conference.wiget.dialog.LoadingDialog;

import java.net.SocketTimeoutException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 基类Fragment,所有Fragment都继承此类
 * 此类集成了动态申请权限的方法
 * <p>
 * Created by yangtufa on 2017/7/19.
 */

public abstract class BaseFragment extends Fragment implements BaseView, EasyPermissions.PermissionCallbacks {

    private static final int REQUEST_GET_PERMISSION = 333;
    private Unbinder unBinder;
    private String[] perms;
    private String requestPermissionText;
    private FragmentLazyLoad fragmentLazyLoad = null;
    private boolean isLazyLoad;
    private LoadingDialog loadingDialog;
    private AnimationDrawable loadingAnim;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLazyLoad = needLazyLoad();
        if (isLazyLoad) {
            fragmentLazyLoad = new FragmentLazyLoad(new FragmentLazyLoad.ReadyCallback() {
                @Override
                public void lazyLoad() {
                    doAfterLazyLoad();
                    fragmentLazyLoad.setFirst(true);
                }
            }, false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(getLayout(), container, false);
        unBinder = ButterKnife.bind(this, fragmentView);

        initInject();

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        if (isLazyLoad && fragmentLazyLoad != null) {
            fragmentLazyLoad.setViewIsReady(true);
            fragmentLazyLoad.intentToLazyLoad();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isLazyLoad) {
            if (getUserVisibleHint() && fragmentLazyLoad != null) {
                fragmentLazyLoad.setVisible(true);
                fragmentLazyLoad.intentToLazyLoad();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unBinder != null) {
            unBinder.unbind();
        }
    }

    public FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .applicationComponent(ConApplication.getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showLong(msg);
    }

    @Override
    public void showError(String error) {
        ToastUtils.showLong(error);
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

    @Override
    public void setPagerParams(PagerResult pagerResult) {
        //not implemented
    }

    @Override
    public void setPagerParams(FeedResult<PagerResult> feedResult) {
        //not implemented
    }

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

    @Override
    public void setToolBar(Toolbar toolBar, String title) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolBar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        TextView tvTitle = (TextView) toolBar.findViewById(R.id.tv_title);
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    @Override
    public void setToolBar(Toolbar toolBar, String title, boolean needBackButton) {

    }

    /***权限申请:在页面中申请需要的权限可以直接调用此方法并且重写相关的方法接收授权的结果即可*/
    public void getPhonePermission(String[] perms, String requestPermissionText) {
        this.perms = perms;
        this.requestPermissionText = requestPermissionText;
        getPermission();
    }

    private void getPermission() {
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
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
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        if (loadingAnim != null) {
            loadingAnim.stop();
            loadingAnim = null;
        }
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity(), R.layout.dialog_loading_window);
        }
        ImageView ivLoading = (ImageView) loadingDialog.getView(R.id.iv_loading_image);
        ivLoading.setBackgroundResource(R.drawable.anim_loading_dialog);
        TextView tvTip = (TextView) loadingDialog.getView(R.id.tv_tips);
        tvTip.setText(tipText);
        loadingAnim = (AnimationDrawable) ivLoading.getBackground();
        loadingAnim.start();
        loadingDialog.show();
    }

    @Override
    public void dismissLoadingDialog() {
        if (loadingDialog != null) loadingDialog.dismiss();
        if (loadingAnim != null) loadingAnim.stop();
    }

    /***是否需要懒加载，默认实现懒加载操作*/
    protected boolean needLazyLoad() {
        return false;
    }

    /***懒加载回来后要做的事情,如果lazyload为true要写*/
    protected void doAfterLazyLoad() {
    }

    protected View getEmptyView(String tips) {
        View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.view_empty_view, null);
        TextView tvTips = (TextView) emptyView.findViewById(R.id.tv_tips);
        tvTips.setText(tips);
        return emptyView;
    }

    protected View getEmptyView() {
        return getEmptyView("暂无数据");
    }

    protected abstract void initInject();

    protected abstract int getLayout();

    protected abstract void initEventAnSimpledData();
}
