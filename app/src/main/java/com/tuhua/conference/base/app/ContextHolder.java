package com.tuhua.conference.base.app;

/**
 * ApplicationContext 持有者,为了方便以后改造Application类,
 * <p>
 * 不直接将获取Application实例的方法定义在 {@link EntApplication}中
 * <p>
 * Created by yangtufa on 2017/3/25.
 */

public class ContextHolder {

    private static EntApplication mApplication;

    /***向外提供获取Application实例的方法*/
    public static synchronized EntApplication getApplication() {
        return mApplication;
    }

    /***设置Application*/
    public static void setApplication(EntApplication application) {
        mApplication = application;
    }
}
