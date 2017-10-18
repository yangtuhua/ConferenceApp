package com.tuhua.conference.base.config;

import com.blankj.utilcode.util.LogUtils;
import com.tuhua.conference.http.EntApi;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 接口请求配置,用户读取config.properties中的配置
 * <p>
 * Created by yangtufa on 2017/4/13.
 */

public class UrlConfig {
    private static final Map<String, String> configProperties;

    static {
        Properties properties = new Properties();
        configProperties = new HashMap<>();
        try {
            properties.load(UrlConfig.class.getClassLoader().getResourceAsStream("assets/config.properties"));
            Enumeration em1 = properties.propertyNames();
            while (em1.hasMoreElements()) {
                String key = (String) em1.nextElement();
                configProperties.put(key, properties.getProperty(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e("读取配置文件失败！");
        }
    }

    /***根据key获取value*/
    public static String getValueByKey(String key) {
        return configProperties.get(key);
    }

    /***向外部提供获取接口的方法*/
    public static String getUrl(String key) {
        return getBaseUrl() + configProperties.get(key);
    }

    /***向外提供获取baseUrl的方法*/
    public static String getBaseUrl() {
        return EntApi.HOST;
    }
}
