package com.tuhua.conference.http;


import com.tuhua.conference.BuildConfig;
import com.tuhua.conference.base.config.UrlConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import okhttp3.Cookie;

/**
 * 此类用于保存用户的 {@link Cookie}信息
 * 在请求网络返回时,HttpModule 类中保存cookie信息
 * <p>
 * Created by yangtufa on 2017/4/5.
 */

public class CookieHolder {
    private static CopyOnWriteArrayList<Cookie> mCookies;

    /***向外保存cookie的方法*/
    public static void setCooKieList(List<Cookie> cookies) {
        if (mCookies == null) {
            mCookies = new CopyOnWriteArrayList<>();
        }
        String ssoKey;
        if (BuildConfig.DEBUG) {
            ssoKey = UrlConfig.getValueByKey("testSSOCookieName");
        } else {
            ssoKey = UrlConfig.getValueByKey("outerSSOCookieName");
        }

        //Fixme 暂时这么处理,需要优化
        for (Cookie newCookie : cookies) {
            if (newCookie.name().contains("JSESSIONID")) {
                //移动原来集合中的JSESSIONID
                if (mCookies != null) {
                    for (Cookie oldCookie : mCookies) {
                        if (oldCookie.name().contains("JSESSIONID")) {
                            mCookies.remove(oldCookie);
                        }
                    }
                }
            }

            //移动原来集合中的test_qingpinwo_user_1
            if (newCookie.name().contains(ssoKey)) {
                if (mCookies != null) {
                    for (Cookie oldCookie : mCookies) {
                        if (oldCookie.name().contains(ssoKey)) {
                            mCookies.remove(oldCookie);
                        }
                    }
                }
            }
        }
        mCookies.addAll(cookies);
    }

    /***向外提供获取cookie的方法*/
    public static List<Cookie> getCookies() {
        if (mCookies == null) {
            return new ArrayList<>();
        }
        return mCookies;
    }

    /***向外提供清楚cookie的方法*/
    public static void clearCookie() {
        if (mCookies != null) {
            mCookies.clear();
        }
    }

    public static String getCookieString() {
        StringBuilder builder = new StringBuilder();
        if (mCookies != null) {
            for (int i = 0; i < mCookies.size(); i++) {
                Cookie cookie = mCookies.get(i);
                if (cookie.name().contains("JSESSIONID")) {
                    builder.append("JSESSIONID=").append(cookie.value());
                    if (i != mCookies.size() - 1) {
                        builder.append(",");
                    }
                }
            }
        }
        return builder.toString();
    }

    public static String getSSOKey() {
        if (mCookies == null) {
            mCookies = new CopyOnWriteArrayList<>();
        }
        for (int i = 0; i < mCookies.size(); i++) {
            Cookie cookie = mCookies.get(i);
            //区分生产环境还是测试环境
            if (BuildConfig.DEBUG) {
                if (cookie.name().contains(UrlConfig.getValueByKey("testSSOCookieName"))) {
                    return cookie.value();
                }
            } else {
                if (cookie.name().contains(UrlConfig.getValueByKey("outerSSOCookieName"))) {
                    return cookie.value();
                }
            }
        }
        return "";
    }

}
