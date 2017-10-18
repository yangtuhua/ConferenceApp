package com.tuhua.conference.base.config;

import android.os.Environment;

import java.util.jar.Manifest;

/**
 * 项目基本配置信息
 * Created by yangtufa on 2017/9/13.
 */

public class AppConfig {

    //缓存的顶层目录
    public static final String BASE_CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/conference";

    //log(错误日志)
    public static final String LOG_CACHE = BASE_CACHE_PATH + "/LOG_CACHE";

    //文件下载保存地址
    public static final String DOWNLOAD_FILE_PATH = BASE_CACHE_PATH + "/download_file";

    /***Android7.0文件访问授权key,在 {@link Manifest}清单文件中有配置provider*/
    public static final String FILE_PROVIDER_AUTHORITY = "com.eshine.android.jobenterprise.fileprovider";

}
