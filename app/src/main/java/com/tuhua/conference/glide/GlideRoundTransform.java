package com.tuhua.conference.glide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * 圆角图片转换器
 * <p>
 * Created by yangtufa on 2017/4/1.
 */

public class GlideRoundTransform extends BitmapTransformation {

    private static float radius = 0f;

    /***
     * 默认设置圆角为4dp
     * @param context context
     */
    public GlideRoundTransform(Context context) {
        this(context, 4);
    }

    public GlideRoundTransform(Context context, int dp) {
        super(context);
        radius = Resources.getSystem().getDisplayMetrics().density * dp;
    }


    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    @Override
    public String getId() {
        return getClass().getName() + Math.round(radius);
    }

    private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);

        return result;
    }
}
