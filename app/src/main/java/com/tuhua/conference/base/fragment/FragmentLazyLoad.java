package com.tuhua.conference.base.fragment;

/**
 * Fragment+ViewPager的懒加载辅助类,主要解决预加载的问题，点到fragment了才加载初始数据。
 * Created by liaofucheng on 2017/4/14.
 */

public class FragmentLazyLoad {
    /**
     * fragment是否可见
     */
    private boolean isVisible = false;
    /**
     * view是否实例化
     */
    private boolean viewIsReady = false;
    /**
     * 是否第一次加载数据
     * false 点击到Fragment时不再执行回调方法
     * true 点击到Fragment时执行回调方法
     */
    private boolean isFirst = true;


    /**
     * desc true:自动设置isFirst为false。false:执行完回调后不直接设置isFirst为false。
     *
     * @author liaofucheng on 2017/4/14 18:10
     */
    private boolean autoSetLoaded = true;

    /**
     * 回调
     */
    private ReadyCallback callback;

    private FragmentLazyLoad() {
    }

    /**
     * desc
     *
     * @param callback      回调，要做的任务在这里执行
     * @param autoSetLoaded true:自动设置isFirst为false。false:执行完回调后不直接设置isFirst为false。
     * @author liaofucheng on 2017/4/14 17:53
     */
    public FragmentLazyLoad(ReadyCallback callback, boolean autoSetLoaded) {
        super();
        this.callback = callback;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isViewIsReady() {
        return viewIsReady;
    }

    public void setViewIsReady(boolean viewIsReady) {
        this.viewIsReady = viewIsReady;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isReady() {
        return isVisible && viewIsReady && isFirst;
    }

    public void intentToLazyLoad() {
        if (isReady()) {
            if (callback != null) {
                callback.lazyLoad();
                if (autoSetLoaded) {
                    //上面已经执行过回调了
                    isFirst = false;
                }
            }
        }
    }

    public interface ReadyCallback {
        void lazyLoad();
    }
}
