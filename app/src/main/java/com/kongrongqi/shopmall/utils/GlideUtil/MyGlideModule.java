package com.kongrongqi.shopmall.utils.GlideUtil;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.module.GlideModule;
import com.kongrongqi.shopmall.utils.FileUtils;

/**
 * Created by penny on 2016/10/21 0021.
 */
public class MyGlideModule implements GlideModule {


    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                return DiskLruCacheWrapper.get(FileUtils.createCacheImagePath(),50*1024*1024);
            }
        });
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
