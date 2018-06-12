package com.kongrongqi.shopmall.utils.GlideUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.utils.Logger;


/**
 * Created by Administrator on 2016/5/30 0030.
 */
public class GlideUtils {
    private static GlideUtils sGlideUtils;

    public static GlideUtils getInstance() {
        if (sGlideUtils == null)
            synchronized (GlideUtils.class) {
                if (sGlideUtils == null) {
                    sGlideUtils = new GlideUtils();
                }
            }
        return sGlideUtils;
    }

    /**
     * 普通加载网络图片
     *
     * @param context
     * @param image
     * @param url
     */
    public void loadImage(Context context, ImageView image, String url) {
        Glide.with(context)
                .load(url)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(image);
    }

    /**
     * 加载网络图片 并设置圆形
     *
     * @param context
     * @param image
     * @param url
     */
    public void loadImageCircle(Context context, ImageView image, String url) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_launcher)
                .bitmapTransform(new GlideCircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(image);

    }

    /**
     * 加载网络图片 并设置圆形
     *
     * @param context
     * @param url
     */
    public void loadImageCircleCallBack(Context context, String url, final GlideCallBackListener listener) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .transform(new GlideCircleTransform(context))
                .placeholder(R.drawable.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if (null != listener) {
                            listener.onResourceReady(resource, glideAnimation);
                        }
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        Logger.i("GlideUtils", "加载失败...");
                    }
                });

    }

    public interface GlideCallBackListener {
        void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation);
    }


    /**
     * 加载网络图片 并设置正方形
     */
    public void loadImageSquare(Context context, ImageView image, String url) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .bitmapTransform(new GlideSquareTransformation(context))
                .into(image);

    }

    /**
     * 加载圆角矩形
     *
     * @param context 上下文
     * @param image   控件
     * @param url     图片地址
     */
    public void loadImageRectangle(Context context, ImageView image, String url) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .bitmapTransform(new GlideRoundTransform(context))
                .into(image);
    }

    /**
     * 加载圆角矩形
     *
     * @param context 上下文
     * @param image   控件
     * @param url     图片地址
     * @param round   圆角弧度
     */
    public void loadImageRectangle(Context context, ImageView image, String url, int round) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .bitmapTransform(new GlideRoundTransform(context, round))
                .into(image);
    }


    public void loadResourceImage(Context context, ImageView image, int resourceId) {
        Glide.with(context)
                .load(resourceId)
                .asBitmap().
                into(image);
    }

}
