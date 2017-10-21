package com.tuhua.conference.service;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.tuhua.conference.base.app.ContextHolder;

import java.util.Iterator;
import java.util.List;

/**
 * 初始化操作的服务
 * Created by yangtufa on 2017/10/19.
 */

public class InitializationService extends IntentService {

    public static final String ACTION_INITIALIZATION = "InitializationService";

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializationService.class);
        intent.setAction(ACTION_INITIALIZATION);
        context.startService(intent);
    }

    public InitializationService() {
        super("InitializationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        initUtil();

        initEMSDK();

        LogUtils.e("初始化完成！");
    }

    /***初始化环信*/
    private void initEMSDK() {
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(AppUtils.getAppPackageName())) {
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }

        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        EMClient.getInstance().init(ContextHolder.getApplication(), options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    /***初始化工具类*/
    private void initUtil() {
        Utils.init(ContextHolder.getApplication());
    }
}
