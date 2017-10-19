package com.tuhua.conference.service;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.tuhua.conference.BuildConfig;
import com.tuhua.conference.base.app.ContextHolder;

import java.util.Iterator;
import java.util.List;

/**
 * Created by yangtufa on 2017/10/19.
 */

public class InitializationService extends IntentService {

    public static final String ACTION_INITIALIZATION = "InitializationService";

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializationService.class);
        intent.setAction(ACTION_INITIALIZATION);
        context.startService(intent);
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public InitializationService() {
        super("InitializationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        initUtil();

        initEMSDK();
    }

    /***初始化工具类*/
    private void initUtil() {
        Utils.init(ContextHolder.getApplication());
    }

    /***初始化环信*/
    private void initEMSDK() {
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        if (processAppName == null || !processAppName.equalsIgnoreCase(ContextHolder.getApplication().getPackageName())) {
            return;
        }

        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        EMClient.getInstance().init(ContextHolder.getApplication(), options);
        if (BuildConfig.DEBUG) {
            //调试模式
            EMClient.getInstance().setDebugMode(true);
        }
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = getPackageManager();
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
        return null;
    }
}
