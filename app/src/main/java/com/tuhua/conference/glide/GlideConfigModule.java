package com.tuhua.conference.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.tuhua.conference.base.app.EntApplication;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Glide配置信息
 * <p>
 * Created by yangtufa on 2017/3/25.
 */
public class GlideConfigModule implements GlideModule {

    //缓存目录名称
    private static final String DISK_CACHE_NAME = "GLIDE_CACHE";

    //最大缓存磁盘空间
    private static final int DISK_CACHE_MAX_SIZE = 60 * 1024 * 1024;

    //最大内存缓存空间
    private static final int MEMORY_CACHE_MAX_SIZE = 8 * 1024 * 1024;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //本地缓存
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, DISK_CACHE_NAME, DISK_CACHE_MAX_SIZE));
        //内存缓存大小
        builder.setMemoryCache(new LruResourceCache(MEMORY_CACHE_MAX_SIZE));
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //全部的内存缓存用来作为图片缓存
        builder.setBitmapPool(new LruBitmapPool(MEMORY_CACHE_MAX_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        OkHttpClient okHttpClient = EntApplication.getApplicationComponent().getOkhttpClient();
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));
    }
}