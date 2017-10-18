package com.tuhua.conference.glide;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.io.File;

/**
 * 图片工具类
 * <p>
 * Created by yangtufa on 2017/8/10.
 */

public class GlideImageManager {

    /**
     * 图片显示过度的时间
     */
    private static final int CROSS_FADE_TIME = 500;
    private static final float THUMB_SIZE = 0.5f; //0-1之间  10%原图的大小

    /***
     * 显示普通图片
     * @param context 上下文
     * @param imageUrl 图片地址
     * @param imageView imageview
     */
    public static void loadImage(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void loadImage(Context context, File imageFile, ImageView imageView) {
        Glide.with(context)
                .load(imageFile)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /***
     * 显示普通图片
     * @param context 上下文
     * @param imageUri 图片uri
     * @param imageView imageview
     */
    public static void loadImage(Context context, Uri imageUri, ImageView imageView) {
        Glide.with(context)
                .load(imageUri)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /***
     * 显示普通图片
     * @param context 上下文
     * @param imageResId 图片资源id
     * @param imageView imageview
     */
    public static void loadImage(Context context, int imageResId, ImageView imageView) {
        Glide.with(context)
                .load(imageResId)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /***
     * jian加载图片并见设置加载完成监听器
     * @param context context
     * @param imageUrl 图片地址
     * @param imageView imageview
     * @param onImageLoadedListener 加载完成监听器
     */
    public static void loadImage(Context context, String imageUrl, ImageView imageView, final OnImageLoadedListener onImageLoadedListener) {
        Glide.with(context)
                .load(imageUrl)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        //图片加载完成监听器
                        if (onImageLoadedListener != null) {
                            onImageLoadedListener.onImageLoaded();
                        }
                    }
                });
    }

    /***
     * 显示普通图片
     * @param context 上下文
     * @param imageResId 图片资源id
     * @param imageView imageview
     */
    public static void loadRoundImage(Context context, int imageResId, ImageView imageView) {

    }

    /***
     * 显示普通图片
     * @param context 上下文
     * @param imageUrl 图片地址
     * @param imageView imageview
     */
    public static void loadRoundImage(Context context, String imageUrl, ImageView imageView) {

    }

    public interface OnImageLoadedListener {
        void onImageLoaded();
    }
}
